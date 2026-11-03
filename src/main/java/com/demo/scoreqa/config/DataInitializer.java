package com.demo.scoreqa.config;

import com.demo.scoreqa.dao.UserDAO;
import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 启动时初始化演示账号密码。
 * <p>
 * data.sql 中的密码哈希是占位值，无法保证与文档中的明文密码一致。
 * 这里在应用启动后，将演示账号的密码重置为文档约定的明文密码，
 * 确保 admin/admin123、teacher1/123456、student1/123456 一定能登录。
 * 若账号不存在则跳过（不覆盖用户自行修改的密码）。
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    /**
     * data.sql 中写入的占位密码哈希（三个演示账号共用）。
     * 只有当数据库里仍是这个占位值时，才把它重置为文档约定的明文密码；
     * 用户自行修改过密码后（哈希不再是占位值），重启不会覆盖。
     */
    private static final String PLACEHOLDER_HASH =
            "10000:AQIDBAUGBwgJCgsMDQ4PEA==:xvkXCtGR/8VgarLWFb7kPNqVRfFrm+yTV7uZbDDeRKo=";

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void run(String... args) {
        // 先探测一次数据库连接：若连不上（如密码错误），只打印一条清晰的提示，
        // 避免每个账号各抛一次 SQLException 堆栈，干扰排查。
        try (java.sql.Connection ignored = com.demo.scoreqa.util.DBUtil.getConnection()) {
            // 连接正常
        } catch (Exception e) {
            log.error("数据库连接失败，跳过演示账号密码初始化。请检查 MySQL 是否启动、"
                    + "以及 application.yaml / db.properties 中的用户名密码是否正确。原因：{}", e.getMessage());
            return;
        }
        resetPassword("admin", "admin123");
        resetPassword("teacher1", "123456");
        resetPassword("student1", "123456");
    }

    private void resetPassword(String username, String plainPassword) {
        try {
            User user = userDAO.findByUsername(username);
            if (user == null) {
                return;
            }
            // 仅当密码仍是 data.sql 的占位哈希时才重置，避免覆盖用户已修改的密码
            if (PLACEHOLDER_HASH.equals(user.getPassword())) {
                userDAO.updatePassword(user.getId(), PasswordUtil.hash(plainPassword));
                log.info("已初始化演示账号 [{}] 的密码", username);
            }
        } catch (Exception e) {
            log.warn("初始化账号 [{}] 密码失败：{}", username, e.getMessage());
        }
    }

}

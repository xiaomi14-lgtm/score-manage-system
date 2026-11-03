package com.demo.scoreqa.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接工具类（JDBC 三层架构）
 */
public class DBUtil {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("找不到数据库配置文件 db.properties（应位于 classpath 根目录）");
            }
            Properties props = new Properties();
            props.load(in);
            // 优先级：系统属性(-D) > 环境变量 > db.properties
            // 这样无需修改文件即可通过 -Djdbc.password=xxx 或环境变量 DB_PASSWORD 覆盖密码
            driver = resolve(props, "jdbc.driver", "DB_DRIVER");
            url = resolve(props, "jdbc.url", "DB_URL");
            username = resolve(props, "jdbc.username", "DB_USERNAME");
            password = resolve(props, "jdbc.password", "DB_PASSWORD");
            if (driver == null || url == null) {
                throw new IllegalStateException("db.properties 缺少 jdbc.driver 或 jdbc.url 配置");
            }
            Class.forName(driver);
            // 启动时打印实际使用的连接信息（不含密码），便于排查 Access denied 等问题
            System.out.println("[DBUtil] 数据库连接: url=" + url + ", username=" + username
                    + ", password=" + (password == null || password.isEmpty() ? "(空)" : "******"));
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 读取配置：系统属性(-Dkey) 优先，其次环境变量，最后 db.properties。
     */
    private static String resolve(Properties props, String key, String envKey) {
        String v = System.getProperty(key);
        if (v == null || v.isEmpty()) {
            v = System.getenv(envKey);
        }
        if (v == null || v.isEmpty()) {
            v = props.getProperty(key);
        }
        return v;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void close(Connection conn, java.sql.Statement stmt, java.sql.ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
        try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
    }
}

package com.demo.scoreqa.service;

import com.demo.scoreqa.dao.UserDAO;
import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.util.PasswordUtil;

/**
 * 用户业务服务层（登录校验 + 用户管理）。
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * 登录校验：用户名存在、账号启用、密码正确则返回用户，否则返回 null。
     */
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (!user.isEnabled()) {
            return null;
        }
        if (PasswordUtil.verify(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public java.util.List<User> findAll() {
        return userDAO.findAll();
    }

    public java.util.List<User> findByRole(String role) {
        return userDAO.findByRole(role);
    }

    public User findById(Long id) {
        return userDAO.findById(id);
    }

    public boolean save(User user) {
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            user.setPassword(PasswordUtil.hash(user.getPassword()));
        }
        return userDAO.insert(user);
    }

    public boolean update(User user) {
        return userDAO.update(user);
    }

    public boolean changePassword(Long id, String password) {
        return userDAO.updatePassword(id, PasswordUtil.hash(password));
    }

    public boolean delete(Long id) {
        return userDAO.delete(id);
    }
}

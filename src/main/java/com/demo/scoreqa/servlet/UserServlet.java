package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户管理控制器（仅 ADMIN 可操作）。
 */
@WebServlet(name = "UserServlet", urlPatterns = "/userServlet")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "add":
                req.getRequestDispatcher("/WEB-INF/jsp/user/add.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = parseLong(req.getParameter("id"));
                if (id != null) {
                    req.setAttribute("user", userService.findById(id));
                    req.getRequestDispatcher("/WEB-INF/jsp/user/edit.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("userServlet?action=list");
                }
                break;
            case "delete":
                delete(req, resp);
                break;
            default:
                list(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("save".equals(action)) {
            save(req, resp);
        } else if ("update".equals(action)) {
            update(req, resp);
        } else if ("password".equals(action)) {
            changePassword(req, resp);
        } else {
            resp.sendRedirect("userServlet?action=list");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userService.findAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/jsp/user/list.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setRealName(req.getParameter("realName"));
        user.setRole(req.getParameter("role"));
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        user.setStatus(parseInt(req.getParameter("status"), 1));

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()) {
            req.setAttribute("error", "用户名和密码不能为空");
            req.getRequestDispatcher("/WEB-INF/jsp/user/add.jsp").forward(req, resp);
            return;
        }
        userService.save(user);
        resp.sendRedirect("userServlet?action=list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect("userServlet?action=list");
            return;
        }
        User user = userService.findById(id);
        if (user != null) {
            user.setRealName(req.getParameter("realName"));
            user.setRole(req.getParameter("role"));
            user.setEmail(req.getParameter("email"));
            user.setPhone(req.getParameter("phone"));
            user.setStatus(parseInt(req.getParameter("status"), 1));
            userService.update(user);
        }
        resp.sendRedirect("userServlet?action=list");
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        String password = req.getParameter("password");
        if (id != null && password != null && !password.isEmpty()) {
            userService.changePassword(id, password);
        }
        resp.sendRedirect("userServlet?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id != null) {
            userService.delete(id);
        }
        resp.sendRedirect("userServlet?action=list");
    }

    private Long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }
}

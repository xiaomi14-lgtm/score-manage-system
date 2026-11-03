package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录控制器。
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 已经登录则直接跳到首页
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("loginUser") != null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "用户名和密码不能为空");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        User user = userService.login(username.trim(), password);
        if (user == null) {
            req.setAttribute("error", "用户名或密码错误，或账号已被禁用");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("loginUser", user);
        session.setAttribute("role", user.getRole());
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}

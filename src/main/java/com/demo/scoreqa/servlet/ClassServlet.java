package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.Clazz;
import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.service.ClazzService;
import com.demo.scoreqa.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 班级管理控制器。
 */
@WebServlet(name = "ClassServlet", urlPatterns = "/classServlet")
public class ClassServlet extends HttpServlet {

    private final ClazzService clazzService = new ClazzService();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "add":
                req.setAttribute("teachers", userService.findByRole(User.ROLE_TEACHER));
                req.getRequestDispatcher("/WEB-INF/jsp/clazz/add.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = parseLong(req.getParameter("id"));
                if (id != null) {
                    req.setAttribute("clazz", clazzService.findById(id));
                    req.setAttribute("teachers", userService.findByRole(User.ROLE_TEACHER));
                    req.getRequestDispatcher("/WEB-INF/jsp/clazz/edit.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("classServlet?action=list");
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
        } else {
            resp.sendRedirect("classServlet?action=list");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Clazz> classes = clazzService.findAllWithTeacher();
        req.setAttribute("classes", classes);
        req.getRequestDispatcher("/WEB-INF/jsp/clazz/list.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Clazz clazz = new Clazz();
        clazz.setName(req.getParameter("name"));
        clazz.setGrade(req.getParameter("grade"));
        clazz.setTeacherId(parseLong(req.getParameter("teacherId")));
        if (clazz.getName() == null || clazz.getName().trim().isEmpty()) {
            req.setAttribute("error", "班级名称不能为空");
            req.setAttribute("teachers", userService.findByRole(User.ROLE_TEACHER));
            req.getRequestDispatcher("/WEB-INF/jsp/clazz/add.jsp").forward(req, resp);
            return;
        }
        clazzService.save(clazz);
        resp.sendRedirect("classServlet?action=list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect("classServlet?action=list");
            return;
        }
        Clazz clazz = clazzService.findById(id);
        if (clazz != null) {
            clazz.setName(req.getParameter("name"));
            clazz.setGrade(req.getParameter("grade"));
            clazz.setTeacherId(parseLong(req.getParameter("teacherId")));
            clazzService.update(clazz);
        }
        resp.sendRedirect("classServlet?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id != null) {
            clazzService.delete(id);
        }
        resp.sendRedirect("classServlet?action=list");
    }

    private Long parseLong(String s) {
        try {
            return s == null || s.isEmpty() ? null : Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }
}

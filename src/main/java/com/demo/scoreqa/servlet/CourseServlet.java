package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.Course;
import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.service.CourseService;
import com.demo.scoreqa.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 课程管理控制器。
 */
@WebServlet(name = "CourseServlet", urlPatterns = "/courseServlet")
public class CourseServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();
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
                req.getRequestDispatcher("/WEB-INF/jsp/course/add.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = parseLong(req.getParameter("id"));
                if (id != null) {
                    req.setAttribute("course", courseService.findById(id));
                    req.setAttribute("teachers", userService.findByRole(User.ROLE_TEACHER));
                    req.getRequestDispatcher("/WEB-INF/jsp/course/edit.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("courseServlet?action=list");
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
            resp.sendRedirect("courseServlet?action=list");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Course> courses = courseService.findAllWithTeacher();
        req.setAttribute("courses", courses);
        req.getRequestDispatcher("/WEB-INF/jsp/course/list.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = new Course();
        course.setName(req.getParameter("name"));
        course.setCredit(parseDecimal(req.getParameter("credit")));
        course.setTeacherId(parseLong(req.getParameter("teacherId")));

        if (course.getName() == null || course.getName().trim().isEmpty()) {
            req.setAttribute("error", "课程名称不能为空");
            req.setAttribute("teachers", userService.findByRole(User.ROLE_TEACHER));
            req.getRequestDispatcher("/WEB-INF/jsp/course/add.jsp").forward(req, resp);
            return;
        }
        courseService.save(course);
        resp.sendRedirect("courseServlet?action=list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect("courseServlet?action=list");
            return;
        }
        Course course = courseService.findById(id);
        if (course != null) {
            course.setName(req.getParameter("name"));
            course.setCredit(parseDecimal(req.getParameter("credit")));
            course.setTeacherId(parseLong(req.getParameter("teacherId")));
            courseService.save(course);
        }
        resp.sendRedirect("courseServlet?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id != null) {
            courseService.delete(id);
        }
        resp.sendRedirect("courseServlet?action=list");
    }

    private Long parseLong(String s) {
        try {
            return s == null || s.isEmpty() ? null : Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }

    private BigDecimal parseDecimal(String s) {
        try {
            return s == null || s.trim().isEmpty() ? null : new BigDecimal(s.trim());
        } catch (Exception e) {
            return null;
        }
    }
}

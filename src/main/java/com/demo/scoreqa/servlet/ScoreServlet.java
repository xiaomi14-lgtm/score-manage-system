package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.Score;
import com.demo.scoreqa.entity.Student;
import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.service.CourseService;
import com.demo.scoreqa.service.ScoreService;
import com.demo.scoreqa.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * 成绩管理控制器。
 * 支持：列表、录入、编辑、删除，以及学生端查看“我的成绩”。
 */
@WebServlet(name = "ScoreServlet", urlPatterns = "/scoreServlet")
public class ScoreServlet extends HttpServlet {

    private final ScoreService scoreService = new ScoreService();
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "add":
                req.setAttribute("students", studentService.findAllWithClass());
                req.setAttribute("courses", courseService.findAllWithTeacher());
                req.getRequestDispatcher("/WEB-INF/jsp/score/add.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = parseLong(req.getParameter("id"));
                if (id != null) {
                    req.setAttribute("score", scoreService.findByIdWithDetails(id));
                    req.setAttribute("students", studentService.findAllWithClass());
                    req.setAttribute("courses", courseService.findAllWithTeacher());
                    req.getRequestDispatcher("/WEB-INF/jsp/score/edit.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("scoreServlet?action=list");
                }
                break;
            case "delete":
                delete(req, resp);
                break;
            case "my":
                my(req, resp);
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
            resp.sendRedirect("scoreServlet?action=list");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Score> scores = scoreService.findAllWithDetails();
        req.setAttribute("scores", scores);
        req.getRequestDispatcher("/WEB-INF/jsp/score/list.jsp").forward(req, resp);
    }

    /**
     * 学生端：查看当前登录学生自己的成绩。
     */
    private void my(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User loginUser = session == null ? null : (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        Student student = studentService.findByUserId(loginUser.getId());
        List<Score> scores = student == null
                ? java.util.Collections.emptyList()
                : scoreService.findByStudentId(student.getId());
        req.setAttribute("scores", scores);
        req.getRequestDispatcher("/WEB-INF/jsp/score/my.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Score score = new Score();
        score.setStudentId(parseLong(req.getParameter("studentId")));
        score.setCourseId(parseLong(req.getParameter("courseId")));
        score.setScore(parseDecimal(req.getParameter("score")));
        score.setRemark(req.getParameter("remark"));

        if (score.getStudentId() == null || score.getCourseId() == null || score.getScore() == null) {
            req.setAttribute("error", "学生、课程和分数不能为空");
            req.setAttribute("students", studentService.findAllWithClass());
            req.setAttribute("courses", courseService.findAllWithTeacher());
            req.getRequestDispatcher("/WEB-INF/jsp/score/add.jsp").forward(req, resp);
            return;
        }
        scoreService.save(score);
        resp.sendRedirect("scoreServlet?action=list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect("scoreServlet?action=list");
            return;
        }
        Score score = scoreService.findByIdWithDetails(id);
        if (score != null) {
            score.setStudentId(parseLong(req.getParameter("studentId")));
            score.setCourseId(parseLong(req.getParameter("courseId")));
            score.setScore(parseDecimal(req.getParameter("score")));
            score.setRemark(req.getParameter("remark"));
            scoreService.save(score);
        }
        resp.sendRedirect("scoreServlet?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id != null) {
            scoreService.delete(id);
        }
        resp.sendRedirect("scoreServlet?action=list");
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

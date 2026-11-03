package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.Student;
import com.demo.scoreqa.service.ClazzService;
import com.demo.scoreqa.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 学生管理控制器。
 */
@WebServlet(name = "StudentServlet", urlPatterns = "/studentServlet")
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final ClazzService clazzService = new ClazzService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "add":
                req.setAttribute("classes", clazzService.findAllWithTeacher());
                req.getRequestDispatcher("/WEB-INF/jsp/student/add.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = parseLong(req.getParameter("id"));
                if (id != null) {
                    req.setAttribute("student", studentService.findById(id));
                    req.setAttribute("classes", clazzService.findAllWithTeacher());
                    req.getRequestDispatcher("/WEB-INF/jsp/student/edit.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("studentServlet?action=list");
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
            resp.sendRedirect("studentServlet?action=list");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> students = studentService.findAllWithClass();
        req.setAttribute("students", students);
        req.getRequestDispatcher("/WEB-INF/jsp/student/list.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student student = new Student();
        student.setStudentNo(req.getParameter("studentNo"));
        student.setRealName(req.getParameter("realName"));
        student.setClassId(parseLong(req.getParameter("classId")));
        student.setGender(req.getParameter("gender"));

        if (isBlank(student.getStudentNo()) || isBlank(student.getRealName())) {
            req.setAttribute("error", "学号和姓名不能为空");
            req.setAttribute("classes", clazzService.findAllWithTeacher());
            req.getRequestDispatcher("/WEB-INF/jsp/student/add.jsp").forward(req, resp);
            return;
        }
        studentService.save(student);
        resp.sendRedirect("studentServlet?action=list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect("studentServlet?action=list");
            return;
        }
        Student student = studentService.findById(id);
        if (student != null) {
            student.setStudentNo(req.getParameter("studentNo"));
            student.setRealName(req.getParameter("realName"));
            student.setClassId(parseLong(req.getParameter("classId")));
            student.setGender(req.getParameter("gender"));
            studentService.save(student);
        }
        resp.sendRedirect("studentServlet?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id != null) {
            studentService.delete(id);
        }
        resp.sendRedirect("studentServlet?action=list");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private Long parseLong(String s) {
        try {
            return s == null || s.isEmpty() ? null : Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }
}

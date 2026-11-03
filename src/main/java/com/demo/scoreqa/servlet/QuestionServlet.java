package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.Question;
import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 知识库问答控制器。
 */
@WebServlet(name = "QuestionServlet", urlPatterns = "/questionServlet")
public class QuestionServlet extends HttpServlet {

    private final QuestionService questionService = new QuestionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "add":
                req.getRequestDispatcher("/WEB-INF/jsp/kb/add.jsp").forward(req, resp);
                break;
            case "edit":
                Long id = parseLong(req.getParameter("id"));
                if (id != null) {
                    req.setAttribute("question", questionService.findById(id));
                    req.getRequestDispatcher("/WEB-INF/jsp/kb/edit.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect("questionServlet?action=list");
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
            resp.sendRedirect("questionServlet?action=list");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String kw = req.getParameter("keyword");
        List<Question> list = (kw == null || kw.trim().isEmpty())
                ? questionService.findAll()
                : questionService.search(kw.trim());
        req.setAttribute("questions", list);
        req.setAttribute("keyword", kw);
        req.getRequestDispatcher("/WEB-INF/jsp/kb/list.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Question q = new Question();
        q.setQuestion(req.getParameter("question"));
        q.setAnswer(req.getParameter("answer"));
        q.setCategory(req.getParameter("category"));
        HttpSession session = req.getSession(false);
        User login = (session == null) ? null : (User) session.getAttribute("loginUser");
        q.setCreateBy(login == null ? "匿名" : login.getUsername());
        if (q.getQuestion() == null || q.getQuestion().trim().isEmpty()
                || q.getAnswer() == null || q.getAnswer().trim().isEmpty()) {
            req.setAttribute("error", "问题和答案不能为空");
            req.getRequestDispatcher("/WEB-INF/jsp/kb/add.jsp").forward(req, resp);
            return;
        }
        questionService.save(q);
        resp.sendRedirect("questionServlet?action=list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect("questionServlet?action=list");
            return;
        }
        Question q = questionService.findById(id);
        if (q != null) {
            q.setQuestion(req.getParameter("question"));
            q.setAnswer(req.getParameter("answer"));
            q.setCategory(req.getParameter("category"));
            questionService.save(q);
        }
        resp.sendRedirect("questionServlet?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id != null) {
            questionService.delete(id);
        }
        resp.sendRedirect("questionServlet?action=list");
    }

    private Long parseLong(String s) {
        try {
            return (s == null || s.isEmpty()) ? null : Long.parseLong(s);
        } catch (Exception e) {
            return null;
        }
    }
}

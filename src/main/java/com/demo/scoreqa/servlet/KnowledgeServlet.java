package com.demo.scoreqa.servlet;

import com.demo.scoreqa.entity.Knowledge;
import com.demo.scoreqa.entity.User;
import com.demo.scoreqa.service.KnowledgeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 知识库问答控制器（映射到 /questionServlet）。
 */
@WebServlet(name = "KnowledgeServlet", urlPatterns = {"/knowledgeServlet", "/kbServlet"})
public class KnowledgeServlet extends HttpServlet {

    private final KnowledgeService knowledgeService = new KnowledgeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                    req.setAttribute("question", knowledgeService.findById(id));
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
        String keyword = req.getParameter("keyword");
        List<Knowledge> questions = knowledgeService.search(keyword);
        req.setAttribute("questions", questions);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/jsp/kb/list.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Knowledge k = new Knowledge();
        k.setQuestion(req.getParameter("question"));
        k.setAnswer(req.getParameter("answer"));
        k.setCategory(req.getParameter("category"));
        k.setCreateBy(currentUserName(req));

        if (isBlank(k.getQuestion()) || isBlank(k.getAnswer())) {
            req.setAttribute("error", "问题和答案不能为空");
            req.getRequestDispatcher("/WEB-INF/jsp/kb/add.jsp").forward(req, resp);
            return;
        }
        knowledgeService.save(k);
        resp.sendRedirect("questionServlet?action=list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id == null) {
            resp.sendRedirect("questionServlet?action=list");
            return;
        }
        Knowledge k = knowledgeService.findById(id);
        if (k != null) {
            k.setQuestion(req.getParameter("question"));
            k.setAnswer(req.getParameter("answer"));
            k.setCategory(req.getParameter("category"));
            knowledgeService.save(k);
        }
        resp.sendRedirect("questionServlet?action=list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = parseLong(req.getParameter("id"));
        if (id != null) {
            knowledgeService.delete(id);
        }
        resp.sendRedirect("questionServlet?action=list");
    }

    private String currentUserName(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User u = (User) session.getAttribute("loginUser");
            if (u != null) {
                return u.getRealName() != null ? u.getRealName() : u.getUsername();
            }
        }
        return "匿名";
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

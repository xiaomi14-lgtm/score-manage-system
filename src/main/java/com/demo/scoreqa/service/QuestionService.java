package com.demo.scoreqa.service;

import com.demo.scoreqa.dao.QuestionDAO;
import com.demo.scoreqa.entity.Question;

import java.util.List;

/**
 * 知识库问答业务服务层。
 */
public class QuestionService {

    private final QuestionDAO questionDAO = new QuestionDAO();

    public List<Question> findAll() {
        return questionDAO.findAll();
    }

    public List<Question> search(String keyword) {
        return questionDAO.search(keyword);
    }

    public Question findById(Long id) {
        return questionDAO.findById(id);
    }

    public boolean save(Question q) {
        if (q.getId() == null) {
            return questionDAO.insert(q);
        }
        return questionDAO.update(q);
    }

    public boolean delete(Long id) {
        return questionDAO.delete(id);
    }
}

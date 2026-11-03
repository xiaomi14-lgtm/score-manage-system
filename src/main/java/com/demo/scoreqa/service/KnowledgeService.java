package com.demo.scoreqa.service;

import com.demo.scoreqa.dao.KnowledgeDAO;
import com.demo.scoreqa.entity.Knowledge;

import java.util.List;

/**
 * 知识库问答业务服务层。
 */
public class KnowledgeService {

    private final KnowledgeDAO knowledgeDAO = new KnowledgeDAO();

    public List<Knowledge> findAll() {
        return knowledgeDAO.findAll();
    }

    public List<Knowledge> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return knowledgeDAO.findAll();
        }
        return knowledgeDAO.search(keyword.trim());
    }

    public Knowledge findById(Long id) {
        return knowledgeDAO.findById(id);
    }

    public boolean save(Knowledge k) {
        if (k.getId() == null) {
            return knowledgeDAO.insert(k);
        }
        return knowledgeDAO.update(k);
    }

    public boolean delete(Long id) {
        return knowledgeDAO.delete(id);
    }
}

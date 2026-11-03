package com.demo.scoreqa.service;

import com.demo.scoreqa.dao.ScoreDAO;
import com.demo.scoreqa.entity.Score;

import java.util.List;

/**
 * 成绩业务服务层。
 */
public class ScoreService {

    private final ScoreDAO scoreDAO = new ScoreDAO();

    public List<Score> findAllWithDetails() {
        return scoreDAO.findAllWithDetails();
    }

    public List<Score> findByStudentId(Long studentId) {
        return scoreDAO.findByStudentId(studentId);
    }

    public Score findByIdWithDetails(Long id) {
        return scoreDAO.findByIdWithDetails(id);
    }

    public boolean save(Score score) {
        if (score.getId() == null) {
            return scoreDAO.insert(score);
        }
        return scoreDAO.update(score);
    }

    public boolean delete(Long id) {
        return scoreDAO.delete(id);
    }
}

package com.demo.scoreqa.service;

import com.demo.scoreqa.dao.ClazzDAO;
import com.demo.scoreqa.entity.Clazz;

import java.util.List;

/**
 * 班级业务服务层。
 */
public class ClazzService {

    private final ClazzDAO clazzDAO = new ClazzDAO();

    public List<Clazz> findAllWithTeacher() {
        return clazzDAO.findAllWithTeacher();
    }

    public Clazz findById(Long id) {
        return clazzDAO.findById(id);
    }

    public boolean save(Clazz clazz) {
        if (clazz.getId() == null) {
            return clazzDAO.insert(clazz);
        }
        return clazzDAO.update(clazz);
    }

    public boolean update(Clazz clazz) {
        return clazzDAO.update(clazz);
    }

    public boolean delete(Long id) {
        return clazzDAO.delete(id);
    }
}

package com.demo.scoreqa.service;

import com.demo.scoreqa.dao.StudentDAO;
import com.demo.scoreqa.entity.Student;

import java.util.List;

/**
 * 学生业务服务层。
 */
public class StudentService {

    private final StudentDAO studentDAO = new StudentDAO();

    public List<Student> findAllWithClass() {
        return studentDAO.findAllWithClass();
    }

    public List<Student> findByClassId(Long classId) {
        return studentDAO.findByClassId(classId);
    }

    public Student findById(Long id) {
        return studentDAO.findById(id);
    }

    public Student findByUserId(Long userId) {
        return studentDAO.findByUserId(userId);
    }

    public boolean save(Student student) {
        if (student.getId() == null) {
            return studentDAO.insert(student);
        }
        return studentDAO.update(student);
    }

    public boolean delete(Long id) {
        return studentDAO.delete(id);
    }
}

package com.demo.scoreqa.service;

import com.demo.scoreqa.dao.CourseDAO;
import com.demo.scoreqa.entity.Course;

import java.util.List;

/**
 * 课程业务服务层。
 */
public class CourseService {

    private final CourseDAO courseDAO = new CourseDAO();

    public List<Course> findAllWithTeacher() {
        return courseDAO.findAllWithTeacher();
    }

    public Course findById(Long id) {
        return courseDAO.findById(id);
    }

    public boolean save(Course course) {
        if (course.getId() == null) {
            return courseDAO.insert(course);
        }
        return courseDAO.update(course);
    }

    public boolean delete(Long id) {
        return courseDAO.delete(id);
    }
}

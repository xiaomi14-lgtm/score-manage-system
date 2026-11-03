package com.demo.scoreqa.dao;

import com.demo.scoreqa.entity.Course;
import com.demo.scoreqa.util.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程表数据访问对象。
 */
public class CourseDAO {

    public List<Course> findAllWithTeacher() {
        String sql = "SELECT c.*, u.real_name AS teacher_name FROM sys_course c "
                + "LEFT JOIN sys_user u ON c.teacher_id = u.id ORDER BY c.id DESC";
        List<Course> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Course findById(Long id) {
        String sql = "SELECT c.*, u.real_name AS teacher_name FROM sys_course c "
                + "LEFT JOIN sys_user u ON c.teacher_id = u.id WHERE c.id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Course course) {
        String sql = "INSERT INTO sys_course(name, credit, teacher_id, create_time) VALUES(?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getName());
            ps.setBigDecimal(2, course.getCredit() == null ? new BigDecimal("2.0") : course.getCredit());
            ps.setObject(3, course.getTeacherId());
            ps.setObject(4, LocalDateTime.now());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Course course) {
        String sql = "UPDATE sys_course SET name=?, credit=?, teacher_id=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, course.getName());
            ps.setBigDecimal(2, course.getCredit());
            ps.setObject(3, course.getTeacherId());
            ps.setLong(4, course.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM sys_course WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Course mapRow(ResultSet rs) throws SQLException {
        Course c = new Course();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setCredit(rs.getBigDecimal("credit"));
        // teacher_id 可为 NULL，用 getObject 避免 getLong 把 NULL 读成 0
        Object teacherId = rs.getObject("teacher_id");
        c.setTeacherId(teacherId == null ? null : ((Number) teacherId).longValue());
        java.sql.Timestamp ts = rs.getTimestamp("create_time");
        c.setCreateTime(ts == null ? null : ts.toLocalDateTime());
        try { c.setTeacherName(rs.getString("teacher_name")); } catch (Exception ignored) {}
        return c;
    }
}

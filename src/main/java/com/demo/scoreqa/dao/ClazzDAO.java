package com.demo.scoreqa.dao;

import com.demo.scoreqa.entity.Clazz;
import com.demo.scoreqa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 班级表数据访问对象。
 */
public class ClazzDAO {

    public List<Clazz> findAllWithTeacher() {
        String sql = "SELECT c.*, u.real_name AS teacher_name, "
                + "(SELECT COUNT(*) FROM sys_student s WHERE s.class_id = c.id) AS student_count "
                + "FROM sys_class c LEFT JOIN sys_user u ON c.teacher_id = u.id ORDER BY c.id DESC";
        List<Clazz> list = new ArrayList<>();
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

    public Clazz findById(Long id) {
        String sql = "SELECT * FROM sys_class WHERE id = ?";
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

    public boolean insert(Clazz clazz) {
        String sql = "INSERT INTO sys_class(name, grade, teacher_id, create_time) VALUES(?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clazz.getName());
            ps.setString(2, clazz.getGrade());
            ps.setObject(3, clazz.getTeacherId());
            ps.setObject(4, LocalDateTime.now());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Clazz clazz) {
        String sql = "UPDATE sys_class SET name=?, grade=?, teacher_id=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clazz.getName());
            ps.setString(2, clazz.getGrade());
            ps.setObject(3, clazz.getTeacherId());
            ps.setLong(4, clazz.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM sys_class WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Clazz mapRow(ResultSet rs) throws SQLException {
        Clazz c = new Clazz();
        c.setId(rs.getLong("id"));
        c.setName(rs.getString("name"));
        c.setGrade(rs.getString("grade"));
        // teacher_id 可为 NULL，用 getObject 避免 getLong 把 NULL 读成 0
        Object teacherId = rs.getObject("teacher_id");
        c.setTeacherId(teacherId == null ? null : ((Number) teacherId).longValue());
        java.sql.Timestamp ts = rs.getTimestamp("create_time");
        c.setCreateTime(ts == null ? null : ts.toLocalDateTime());
        try { c.setTeacherName(rs.getString("teacher_name")); } catch (Exception ignored) {}
        try { c.setStudentCount(rs.getInt("student_count")); } catch (Exception ignored) {}
        return c;
    }
}

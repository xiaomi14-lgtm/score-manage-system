package com.demo.scoreqa.dao;

import com.demo.scoreqa.entity.Student;
import com.demo.scoreqa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生表数据访问对象。
 */
public class StudentDAO {

    public List<Student> findAllWithClass() {
        String sql = "SELECT s.*, c.name AS class_name FROM sys_student s "
                + "LEFT JOIN sys_class c ON s.class_id = c.id ORDER BY s.id DESC";
        List<Student> list = new ArrayList<>();
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

    public List<Student> findByClassId(Long classId) {
        String sql = "SELECT s.*, c.name AS class_name FROM sys_student s "
                + "LEFT JOIN sys_class c ON s.class_id = c.id WHERE s.class_id = ? ORDER BY s.student_no";
        List<Student> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Student findById(Long id) {
        String sql = "SELECT s.*, c.name AS class_name FROM sys_student s "
                + "LEFT JOIN sys_class c ON s.class_id = c.id WHERE s.id = ?";
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

    /**
     * 按用户 id 查询学生记录（学生登录后关联自己的成绩）。
     */
    public Student findByUserId(Long userId) {
        String sql = "SELECT s.*, c.name AS class_name FROM sys_student s "
                + "LEFT JOIN sys_class c ON s.class_id = c.id WHERE s.user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
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

    public boolean insert(Student student) {
        String sql = "INSERT INTO sys_student(user_id, student_no, real_name, class_id, gender, create_time) "
                + "VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, student.getUserId());
            ps.setString(2, student.getStudentNo());
            ps.setString(3, student.getRealName());
            ps.setObject(4, student.getClassId());
            ps.setString(5, student.getGender());
            ps.setObject(6, LocalDateTime.now());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Student student) {
        String sql = "UPDATE sys_student SET student_no=?, real_name=?, class_id=?, gender=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getStudentNo());
            ps.setString(2, student.getRealName());
            ps.setObject(3, student.getClassId());
            ps.setString(4, student.getGender());
            ps.setLong(5, student.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM sys_student WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getLong("id"));
        // user_id / class_id 可为 NULL，用 getObject 避免 getLong 把 NULL 读成 0
        Object userId = rs.getObject("user_id");
        s.setUserId(userId == null ? null : ((Number) userId).longValue());
        s.setStudentNo(rs.getString("student_no"));
        s.setRealName(rs.getString("real_name"));
        Object classId = rs.getObject("class_id");
        s.setClassId(classId == null ? null : ((Number) classId).longValue());
        s.setGender(rs.getString("gender"));
        java.sql.Timestamp ts = rs.getTimestamp("create_time");
        s.setCreateTime(ts == null ? null : ts.toLocalDateTime());
        try { s.setClassName(rs.getString("class_name")); } catch (Exception ignored) {}
        return s;
    }
}

package com.demo.scoreqa.dao;

import com.demo.scoreqa.entity.Score;
import com.demo.scoreqa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 成绩表数据访问对象。
 */
public class ScoreDAO {

    public List<Score> findAllWithDetails() {
        String sql = "SELECT sc.*, st.student_no, st.real_name AS student_name, co.name AS course_name "
                + "FROM sys_score sc "
                + "LEFT JOIN sys_student st ON sc.student_id = st.id "
                + "LEFT JOIN sys_course co ON sc.course_id = co.id "
                + "ORDER BY sc.id DESC";
        List<Score> list = new ArrayList<>();
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

    /**
     * 按学生查询成绩（供学生端查看自己成绩）。
     */
    public List<Score> findByStudentId(Long studentId) {
        String sql = "SELECT sc.*, st.student_no, st.real_name AS student_name, co.name AS course_name "
                + "FROM sys_score sc "
                + "LEFT JOIN sys_student st ON sc.student_id = st.id "
                + "LEFT JOIN sys_course co ON sc.course_id = co.id "
                + "WHERE sc.student_id = ? ORDER BY sc.exam_time DESC";
        List<Score> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, studentId);
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

    /**
     * 按 ID 查询成绩（含学生、课程冗余信息）。
     */
    public Score findByIdWithDetails(Long id) {
        String sql = "SELECT sc.*, st.student_no, st.real_name AS student_name, co.name AS course_name "
                + "FROM sys_score sc "
                + "LEFT JOIN sys_student st ON sc.student_id = st.id "
                + "LEFT JOIN sys_course co ON sc.course_id = co.id "
                + "WHERE sc.id = ?";
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

    public boolean insert(Score score) {
        String sql = "INSERT INTO sys_score(student_id, course_id, score, exam_time, remark) VALUES(?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, score.getStudentId());
            ps.setLong(2, score.getCourseId());
            ps.setBigDecimal(3, score.getScore());
            ps.setObject(4, score.getExamTime() == null ? LocalDateTime.now() : score.getExamTime());
            ps.setString(5, score.getRemark());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Score score) {
        String sql = "UPDATE sys_score SET student_id=?, course_id=?, score=?, exam_time=?, remark=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, score.getStudentId());
            ps.setLong(2, score.getCourseId());
            ps.setBigDecimal(3, score.getScore());
            ps.setObject(4, score.getExamTime() == null ? LocalDateTime.now() : score.getExamTime());
            ps.setString(5, score.getRemark());
            ps.setLong(6, score.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM sys_score WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Score mapRow(ResultSet rs) throws SQLException {
        Score s = new Score();
        s.setId(rs.getLong("id"));
        s.setStudentId(rs.getLong("student_id"));
        s.setCourseId(rs.getLong("course_id"));
        s.setScore(rs.getBigDecimal("score"));
        s.setRemark(rs.getString("remark"));
        java.sql.Timestamp ts = rs.getTimestamp("exam_time");
        s.setExamTime(ts == null ? null : ts.toLocalDateTime());
        try { s.setStudentNo(rs.getString("student_no")); } catch (Exception ignored) {}
        try { s.setStudentName(rs.getString("student_name")); } catch (Exception ignored) {}
        try { s.setCourseName(rs.getString("course_name")); } catch (Exception ignored) {}
        return s;
    }
}

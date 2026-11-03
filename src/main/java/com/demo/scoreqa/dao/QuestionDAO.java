package com.demo.scoreqa.dao;

import com.demo.scoreqa.entity.Question;
import com.demo.scoreqa.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 知识库问答数据访问对象。
 * <p>
 * 注意：知识库统一使用 {@code sys_knowledge} 表（与 {@link KnowledgeDAO} 一致）。
 * 早期版本误用了不存在的 {@code kb_question} 表，这里已修正。
 * 实际对外暴露的 Servlet 为 {@code KnowledgeServlet}（映射 /questionServlet），
 * 本类保留以兼容旧代码，但不再引用不存在的表。
 */
public class QuestionDAO {

    public List<Question> findAll() {
        String sql = "SELECT * FROM sys_knowledge ORDER BY id DESC";
        List<Question> list = new ArrayList<>();
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

    public List<Question> search(String keyword) {
        String sql = "SELECT * FROM sys_knowledge WHERE question LIKE ? OR answer LIKE ? ORDER BY id DESC";
        List<Question> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
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

    public Question findById(Long id) {
        String sql = "SELECT * FROM sys_knowledge WHERE id = ?";
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

    public boolean insert(Question q) {
        String sql = "INSERT INTO sys_knowledge(question, answer, category, create_by, create_time) VALUES(?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, q.getQuestion());
            ps.setString(2, q.getAnswer());
            ps.setString(3, q.getCategory());
            ps.setString(4, q.getCreateBy());
            ps.setObject(5, LocalDateTime.now());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Question q) {
        String sql = "UPDATE sys_knowledge SET question=?, answer=?, category=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, q.getQuestion());
            ps.setString(2, q.getAnswer());
            ps.setString(3, q.getCategory());
            ps.setLong(4, q.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM sys_knowledge WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Question mapRow(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setId(rs.getLong("id"));
        q.setQuestion(rs.getString("question"));
        q.setAnswer(rs.getString("answer"));
        q.setCategory(rs.getString("category"));
        q.setCreateBy(rs.getString("create_by"));
        // 修正：从结果集读取 create_time，而不是用当前时间
        java.sql.Timestamp ts = rs.getTimestamp("create_time");
        q.setCreateTime(ts == null ? null : ts.toLocalDateTime());
        return q;
    }
}

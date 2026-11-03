package com.demo.scoreqa.dao;

import com.demo.scoreqa.entity.Knowledge;
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
 */
public class KnowledgeDAO {

    public List<Knowledge> findAll() {
        String sql = "SELECT * FROM sys_knowledge ORDER BY id DESC";
        return query(sql, null);
    }

    /**
     * 关键字检索（问题或答案包含关键字）。
     */
    public List<Knowledge> search(String keyword) {
        String sql = "SELECT * FROM sys_knowledge WHERE question LIKE ? OR answer LIKE ? ORDER BY id DESC";
        String like = "%" + keyword + "%";
        return query(sql, new String[]{like, like});
    }

    public Knowledge findById(Long id) {
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

    public boolean insert(Knowledge k) {
        String sql = "INSERT INTO sys_knowledge(question, answer, category, create_by, create_time) VALUES(?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getQuestion());
            ps.setString(2, k.getAnswer());
            ps.setString(3, k.getCategory());
            ps.setString(4, k.getCreateBy());
            ps.setObject(5, LocalDateTime.now());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Knowledge k) {
        String sql = "UPDATE sys_knowledge SET question=?, answer=?, category=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getQuestion());
            ps.setString(2, k.getAnswer());
            ps.setString(3, k.getCategory());
            ps.setLong(4, k.getId());
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

    private List<Knowledge> query(String sql, String[] params) {
        List<Knowledge> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setString(i + 1, params[i]);
                }
            }
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

    private Knowledge mapRow(ResultSet rs) throws SQLException {
        Knowledge k = new Knowledge();
        k.setId(rs.getLong("id"));
        k.setQuestion(rs.getString("question"));
        k.setAnswer(rs.getString("answer"));
        k.setCategory(rs.getString("category"));
        k.setCreateBy(rs.getString("create_by"));
        java.sql.Timestamp ts = rs.getTimestamp("create_time");
        k.setCreateTime(ts == null ? null : ts.toLocalDateTime());
        return k;
    }
}

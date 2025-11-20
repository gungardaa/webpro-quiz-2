package dao;

import model.Tugas;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TugasDAO {

    public boolean create(Tugas t) {
        String sql = "INSERT INTO tugas (mata_kuliah_id, judul_tugas, deskripsi, deadline, status, file_path) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getMataKuliahId());
            ps.setString(2, t.getJudulTugas());
            ps.setString(3, t.getDeskripsi());
            ps.setTimestamp(4, t.getDeadline());
            ps.setString(5, t.getStatus() == null ? "belum" : t.getStatus());
            ps.setString(6, t.getFilePath());
            int aff = ps.executeUpdate();
            if (aff > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) t.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Tugas> allByUser(int userId) {
        List<Tugas> list = new ArrayList<>();
        String sql = "SELECT t.* FROM tugas t JOIN mata_kuliah m ON t.mata_kuliah_id = m.id WHERE m.user_id = ? ORDER BY t.deadline ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tugas t = new Tugas();
                    t.setId(rs.getInt("id"));
                    t.setMataKuliahId(rs.getInt("mata_kuliah_id"));
                    t.setJudulTugas(rs.getString("judul_tugas"));
                    t.setDeskripsi(rs.getString("deskripsi"));
                    t.setDeadline(rs.getTimestamp("deadline"));
                    t.setStatus(rs.getString("status"));
                    t.setFilePath(rs.getString("file_path"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    t.setUpdatedAt(rs.getTimestamp("updated_at"));
                    list.add(t);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Tugas findById(int id) {
        String sql = "SELECT * FROM tugas WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tugas t = new Tugas();
                    t.setId(rs.getInt("id"));
                    t.setMataKuliahId(rs.getInt("mata_kuliah_id"));
                    t.setJudulTugas(rs.getString("judul_tugas"));
                    t.setDeskripsi(rs.getString("deskripsi"));
                    t.setDeadline(rs.getTimestamp("deadline"));
                    t.setStatus(rs.getString("status"));
                    t.setFilePath(rs.getString("file_path"));
                    t.setCreatedAt(rs.getTimestamp("created_at"));
                    t.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return t;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean update(Tugas t) {
        String sql = "UPDATE tugas SET mata_kuliah_id = ?, judul_tugas = ?, deskripsi = ?, deadline = ?, status = ?, file_path = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getMataKuliahId());
            ps.setString(2, t.getJudulTugas());
            ps.setString(3, t.getDeskripsi());
            ps.setTimestamp(4, t.getDeadline());
            ps.setString(5, t.getStatus());
            ps.setString(6, t.getFilePath());
            ps.setInt(7, t.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM tugas WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public int countByUserAndStatus(int userId, String status) {
        String sql = "SELECT COUNT(*) AS cnt FROM tugas t JOIN mata_kuliah m ON t.mata_kuliah_id = m.id WHERE m.user_id = ? AND t.status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int countTotalByUser(int userId) {
        String sql = "SELECT COUNT(*) AS cnt FROM tugas t JOIN mata_kuliah m ON t.mata_kuliah_id = m.id WHERE m.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}

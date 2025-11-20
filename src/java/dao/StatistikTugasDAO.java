package dao;

import model.StatistikTugas;
import util.DBConnection;

import java.sql.*;

public class StatistikTugasDAO {

    public boolean upsert(int userId, int total, int selesai, int belum) {
        String selectSql = "SELECT id FROM statistik_tugas WHERE user_id = ?";
        String insertSql = "INSERT INTO statistik_tugas (user_id, total_tugas, tugas_selesai, tugas_belum) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE statistik_tugas SET total_tugas = ?, tugas_selesai = ?, tugas_belum = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        try (PreparedStatement ups = conn.prepareStatement(updateSql)) {
                            ups.setInt(1, total);
                            ups.setInt(2, selesai);
                            ups.setInt(3, belum);
                            ups.setInt(4, userId);
                            return ups.executeUpdate() > 0;
                        }
                    } else {
                        try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                            ins.setInt(1, userId);
                            ins.setInt(2, total);
                            ins.setInt(3, selesai);
                            ins.setInt(4, belum);
                            return ins.executeUpdate() > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public StatistikTugas findByUser(int userId) {
        String sql = "SELECT * FROM statistik_tugas WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StatistikTugas s = new StatistikTugas();
                    s.setId(rs.getInt("id"));
                    s.setUserId(rs.getInt("user_id"));
                    s.setTotalTugas(rs.getInt("total_tugas"));
                    s.setTugasSelesai(rs.getInt("tugas_selesai"));
                    s.setTugasBelum(rs.getInt("tugas_belum"));
                    s.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return s;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}

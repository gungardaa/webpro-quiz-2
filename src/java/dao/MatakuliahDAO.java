package dao;

import model.MataKuliah;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatakuliahDAO {

    public boolean create(MataKuliah m) {
        String sql = "INSERT INTO mata_kuliah (user_id, kode_matkul, nama_matkul) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getUserId());
            ps.setString(2, m.getKodeMatkul());
            ps.setString(3, m.getNamaMatkul());
            int aff = ps.executeUpdate();
            if (aff > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) m.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<MataKuliah> allByUser(int userId) {
        List<MataKuliah> list = new ArrayList<>();
        String sql = "SELECT * FROM mata_kuliah WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MataKuliah m = new MataKuliah();
                    m.setId(rs.getInt("id"));
                    m.setUserId(rs.getInt("user_id"));
                    m.setKodeMatkul(rs.getString("kode_matkul"));
                    m.setNamaMatkul(rs.getString("nama_matkul"));
                    m.setCreatedAt(rs.getTimestamp("created_at"));
                    m.setUpdatedAt(rs.getTimestamp("updated_at"));
                    list.add(m);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public MataKuliah findById(int id) {
        String sql = "SELECT * FROM mata_kuliah WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MataKuliah m = new MataKuliah();
                    m.setId(rs.getInt("id"));
                    m.setUserId(rs.getInt("user_id"));
                    m.setKodeMatkul(rs.getString("kode_matkul"));
                    m.setNamaMatkul(rs.getString("nama_matkul"));
                    m.setCreatedAt(rs.getTimestamp("created_at"));
                    m.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return m;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean update(MataKuliah m) {
        String sql = "UPDATE mata_kuliah SET kode_matkul = ?, nama_matkul = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getKodeMatkul());
            ps.setString(2, m.getNamaMatkul());
            ps.setInt(3, m.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM mata_kuliah WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}

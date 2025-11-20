package model;

import java.sql.Timestamp;

public class StatistikTugas {
    private int id;
    private int userId;
    private int totalTugas;
    private int tugasSelesai;
    private int tugasBelum;
    private Timestamp updatedAt;

    public StatistikTugas() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getTotalTugas() { return totalTugas; }
    public void setTotalTugas(int totalTugas) { this.totalTugas = totalTugas; }
    public int getTugasSelesai() { return tugasSelesai; }
    public void setTugasSelesai(int tugasSelesai) { this.tugasSelesai = tugasSelesai; }
    public int getTugasBelum() { return tugasBelum; }
    public void setTugasBelum(int tugasBelum) { this.tugasBelum = tugasBelum; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}

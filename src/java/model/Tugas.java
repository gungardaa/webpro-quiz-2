package model;

import java.sql.Timestamp;
import java.util.Date;

public class Tugas {
    private int id;
    private int mataKuliahId;
    private String judulTugas;
    private String deskripsi;
    private Timestamp deadline;
    private String status;
    private String filePath;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Tugas() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMataKuliahId() { return mataKuliahId; }
    public void setMataKuliahId(int mataKuliahId) { this.mataKuliahId = mataKuliahId; }
    public String getJudulTugas() { return judulTugas; }
    public void setJudulTugas(String judulTugas) { this.judulTugas = judulTugas; }
    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
    public Timestamp getDeadline() { return deadline; }
    public void setDeadline(Timestamp deadline) { this.deadline = deadline; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}

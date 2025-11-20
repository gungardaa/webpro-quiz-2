package model;

import java.sql.Timestamp;

public class MataKuliah {
    private int id;
    private int userId;
    private String kodeMatkul;
    private String namaMatkul;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public MataKuliah() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getKodeMatkul() { return kodeMatkul; }
    public void setKodeMatkul(String kodeMatkul) { this.kodeMatkul = kodeMatkul; }
    public String getNamaMatkul() { return namaMatkul; }
    public void setNamaMatkul(String namaMatkul) { this.namaMatkul = namaMatkul; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}

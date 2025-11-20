package controller;

import dao.MatakuliahDAO;
import dao.TugasDAO;
import dao.StatistikTugasDAO;
import model.MataKuliah;
import model.Tugas;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/tugas/*")
@MultipartConfig
public class TugasController extends HttpServlet {
    private TugasDAO tugasDAO = new TugasDAO();
    private MatakuliahDAO mkDAO = new MatakuliahDAO();
    private StatistikTugasDAO statistikDAO = new StatistikTugasDAO();

    private String uploadsDir(HttpServletRequest req) {
        String path = req.getServletContext().getRealPath("/") + File.separator + "uploads";
        File d = new File(path);
        if (!d.exists()) d.mkdirs();
        return path;
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath()+"/users/login.jsp"); return; }

        String path = req.getPathInfo();
        if (path == null || "/list".equals(path) || "/".equals(path)) {
            List<Tugas> list = tugasDAO.allByUser(user.getId());
            req.setAttribute("list", list);
            req.getRequestDispatcher("/tugas/list.jsp").forward(req, resp);
        } else if ("/add".equals(path)) {
            List<MataKuliah> mk = mkDAO.allByUser(user.getId());
            req.setAttribute("mks", mk);
            req.getRequestDispatcher("/tugas/add.jsp").forward(req, resp);
        } else if ("/edit".equals(path)) {
            String idS = req.getParameter("id");
            if (idS != null) {
                Tugas t = tugasDAO.findById(Integer.parseInt(idS));
                req.setAttribute("t", t);
                req.setAttribute("mks", mkDAO.allByUser(user.getId()));
                req.getRequestDispatcher("/tugas/edit.jsp").forward(req, resp);
            } else resp.sendRedirect(req.getContextPath()+"/tugas/list");
        } else if ("/delete".equals(path)) {
            String idS = req.getParameter("id");
            if (idS != null) {
                Tugas t = tugasDAO.findById(Integer.parseInt(idS));
                if (t != null && t.getFilePath() != null) {
                    File f = new File(uploadsDir(req) + File.separator + t.getFilePath());
                    if (f.exists()) f.delete();
                }
                tugasDAO.delete(Integer.parseInt(idS));
            }
            updateStatistik(user.getId());
            resp.sendRedirect(req.getContextPath()+"/tugas/list");
        } else {
            resp.sendError(404);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath()+"/users/login.jsp"); return; }

        String path = req.getPathInfo();
        if ("/add".equals(path)) {
            Tugas t = new Tugas();
            t.setMataKuliahId(Integer.parseInt(req.getParameter("mata_kuliah_id")));
            t.setJudulTugas(req.getParameter("judul_tugas"));
            t.setDeskripsi(req.getParameter("deskripsi"));
            String dl = req.getParameter("deadline");
            if (dl != null && !dl.isEmpty()) {
                try {
                    LocalDateTime ldt = LocalDateTime.parse(dl, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    t.setDeadline(Timestamp.valueOf(ldt));
                } catch (Exception e) {
                    try {
                        LocalDateTime ldt = LocalDateTime.parse(dl, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        t.setDeadline(Timestamp.valueOf(ldt));
                    } catch (Exception e2) {}
                }
            }
            t.setStatus("belum");
            Part filePart = req.getPart("file");
            if (filePart != null && filePart.getSize() > 0) {
                String submitted = PathUtil.getFilename(filePart);
                String savedName = System.currentTimeMillis() + "_" + submitted;
                filePart.write(uploadsDir(req) + File.separator + savedName);
                t.setFilePath(savedName);
            }
            tugasDAO.create(t);
            updateStatistik(user.getId());
            resp.sendRedirect(req.getContextPath()+"/tugas/list");
        } else if ("/edit".equals(path)) {
            Tugas t = tugasDAO.findById(Integer.parseInt(req.getParameter("id")));
            if (t != null) {
                t.setMataKuliahId(Integer.parseInt(req.getParameter("mata_kuliah_id")));
                t.setJudulTugas(req.getParameter("judul_tugas"));
                t.setDeskripsi(req.getParameter("deskripsi"));
                String dl = req.getParameter("deadline");
                if (dl != null && !dl.isEmpty()) {
                    try {
                        LocalDateTime ldt = LocalDateTime.parse(dl, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                        t.setDeadline(Timestamp.valueOf(ldt));
                    } catch (Exception e) {}
                }
                String status = req.getParameter("status");
                if (status != null) t.setStatus(status);
                Part filePart = req.getPart("file");
                if (filePart != null && filePart.getSize() > 0) {
                    if (t.getFilePath() != null) {
                        File old = new File(uploadsDir(req) + File.separator + t.getFilePath());
                        if (old.exists()) old.delete();
                    }
                    String submitted = PathUtil.getFilename(filePart);
                    String savedName = System.currentTimeMillis() + "_" + submitted;
                    filePart.write(uploadsDir(req) + File.separator + savedName);
                    t.setFilePath(savedName);
                }
                tugasDAO.update(t);
            }
            updateStatistik(user.getId());
            resp.sendRedirect(req.getContextPath()+"/tugas/list");
        } else {
            resp.sendError(404);
        }
    }

    private void updateStatistik(int userId) {
        int total = tugasDAO.countTotalByUser(userId);
        int selesai = tugasDAO.countByUserAndStatus(userId, "selesai");
        int belum = tugasDAO.countByUserAndStatus(userId, "belum");
        statistikDAO.upsert(userId, total, selesai, belum);
    }
}

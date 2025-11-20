package controller;

import dao.MatakuliahDAO;
import model.MataKuliah;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/matakuliah/*")
public class MataKuliahController extends HttpServlet {
    private MatakuliahDAO mdao = new MatakuliahDAO();

    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath()+"/users/login.jsp"); return; }

        String path = req.getPathInfo();
        if (path == null || "/list".equals(path) || "/".equals(path)) {
            List<MataKuliah> list = mdao.allByUser(user.getId());
            req.setAttribute("list", list);
            req.getRequestDispatcher("/matakuliah/list.jsp").forward(req, resp);
        } else if ("/add".equals(path)) {
            req.getRequestDispatcher("/matakuliah/add.jsp").forward(req, resp);
        } else if ("/edit".equals(path)) {
            String idS = req.getParameter("id");
            if (idS != null) {
                MataKuliah m = mdao.findById(Integer.parseInt(idS));
                req.setAttribute("m", m);
                req.getRequestDispatcher("/matakuliah/edit.jsp").forward(req, resp);
            } else resp.sendRedirect(req.getContextPath()+"/matakuliah/list");
        } else if ("/delete".equals(path)) {
            String idS = req.getParameter("id");
            if (idS != null) {
                mdao.delete(Integer.parseInt(idS));
            }
            resp.sendRedirect(req.getContextPath()+"/matakuliah/list");
        } else {
            resp.sendError(404);
        }
    }

    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = session == null ? null : (User) session.getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath()+"/users/login.jsp"); return; }

        String path = req.getPathInfo();
        if ("/add".equals(path)) {
            MataKuliah m = new MataKuliah();
            m.setUserId(user.getId());
            m.setKodeMatkul(req.getParameter("kode_matkul"));
            m.setNamaMatkul(req.getParameter("nama_matkul"));
            mdao.create(m);
            resp.sendRedirect(req.getContextPath()+"/matakuliah/list");
        } else if ("/edit".equals(path)) {
            MataKuliah m = new MataKuliah();
            m.setId(Integer.parseInt(req.getParameter("id")));
            m.setKodeMatkul(req.getParameter("kode_matkul"));
            m.setNamaMatkul(req.getParameter("nama_matkul"));
            mdao.update(m);
            resp.sendRedirect(req.getContextPath()+"/matakuliah/list");
        } else {
            resp.sendError(404);
        }
    }
}

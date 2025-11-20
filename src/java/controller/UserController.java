package controller;

import dao.UserDAO;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/users/*")
public class UserController extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || "/login".equals(path) || "/".equals(path)) {
            req.getRequestDispatcher("/users/login.jsp").forward(req, resp);
        } else if ("/register".equals(path)) {
            req.getRequestDispatcher("/users/register.jsp").forward(req, resp);
        } else if ("/logout".equals(path)) {
            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();
            resp.sendRedirect(req.getContextPath()+"/index.html");
        } else {
            resp.sendError(404);
        }
    }

    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/login".equals(path)) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User u = userDAO.authenticate(email, password);
            if (u != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("user", u);
                resp.sendRedirect(req.getContextPath() + "/dashboard/dashboard.jsp");
            } else {
                req.setAttribute("error", "Email atau password salah");
                req.getRequestDispatcher("/users/login.jsp").forward(req, resp);
            }
        } else if ("/register".equals(path)) {
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            User u = new User();
            u.setName(name);
            u.setEmail(email);
            u.setPassword(password);
            boolean ok = userDAO.register(u);
            if (ok) {
                // auto-login
                HttpSession session = req.getSession(true);
                session.setAttribute("user", userDAO.findById(u.getId()));
                resp.sendRedirect(req.getContextPath() + "/dashboard/dashboard.jsp");
            } else {
                req.setAttribute("error", "Gagal registrasi. Email mungkin sudah terpakai.");
                req.getRequestDispatcher("/users/register.jsp").forward(req, resp);
            }
        } else {
            resp.sendError(404);
        }
    }
}

<%@ page import="model.User, dao.TugasDAO, dao.MatakuliahDAO, dao.StatistikTugasDAO, model.StatistikTugas" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath()+"/users/login.jsp");
        return;
    }
    TugasDAO tdao = new TugasDAO();
    MatakuliahDAO mdao = new MatakuliahDAO();
    StatistikTugasDAO sdao = new StatistikTugasDAO();
    int totalTugas = tdao.countTotalByUser(user.getId());
    int totalMK = mdao.allByUser(user.getId()).size();
    StatistikTugas stat = sdao.findByUser(user.getId());
%>
<html>
<head><title>Dashboard</title></head>
<body>
<h2>Dashboard - Selamat datang, <%= user.getName() %></h2>
<p><a href="<%= request.getContextPath() %>/matakuliah/list">Mata Kuliah</a> |
   <a href="<%= request.getContextPath() %>/tugas/list">Tugas</a> |
   <a href="<%= request.getContextPath() %>/statistik/statistik.jsp">Statistik</a> |
   <a href="<%= request.getContextPath() %>/users/logout">Logout</a>
</p>

<div>
  <h3>Ringkasan</h3>
  <ul>
    <li>Total Mata Kuliah: <%= totalMK %></li>
    <li>Total Tugas: <%= totalTugas %></li>
    <li>Tugas selesai: <%= (stat!=null?stat.getTugasSelesai():0) %></li>
    <li>Tugas belum: <%= (stat!=null?stat.getTugasBelum():0) %></li>
  </ul>
</div>
</body>
</html>

<%@ page import="dao.StatistikTugasDAO, model.StatistikTugas" %>
<%@ page session="true" %>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath()+"/users/login.jsp"); return; }
    StatistikTugasDAO sdao = new StatistikTugasDAO();
    StatistikTugas s = sdao.findByUser(user.getId());
%>
<html>
<head><title>Statistik Tugas</title></head>
<body>
<h2>Statistik Tugas</h2>
<p><a href="<%=request.getContextPath()%>/dashboard/dashboard.jsp">Dashboard</a></p>
<% if (s != null) { %>
  <ul>
    <li>Total tugas: <%= s.getTotalTugas() %></li>
    <li>Tugas selesai: <%= s.getTugasSelesai() %></li>
    <li>Tugas belum: <%= s.getTugasBelum() %></li>
    <li>Terakhir update: <%= s.getUpdatedAt() %></li>
  </ul>
<% } else { %>
  <p>Belum ada statistik.</p>
<% } %>
</body>
</html>

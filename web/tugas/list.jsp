<%@ page import="java.util.List, model.Tugas" %>
<%@ page session="true" %>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath()+"/users/login.jsp"); return; }
    List<Tugas> list = (List<Tugas>) request.getAttribute("list");
%>
<html>
<head><title>Daftar Tugas</title></head>
<body>
<h2>Daftar Tugas</h2>
<p><a href="<%=request.getContextPath()%>/dashboard/dashboard.jsp">Dashboard</a> | <a href="<%=request.getContextPath()%>/tugas/add">Tambah Tugas</a></p>
<table border="1">
<tr><th>ID</th><th>Judul</th><th>Deadline</th><th>Status</th><th>File</th><th>Aksi</th></tr>
<% for (Tugas t : list) { %>
<tr>
  <td><%= t.getId() %></td>
  <td><%= t.getJudulTugas() %></td>
  <td><%= t.getDeadline() %></td>
  <td><%= t.getStatus() %></td>
  <td><% if (t.getFilePath() != null) { %>
        <a href="<%=request.getContextPath()%>/uploads/<%= t.getFilePath() %>">Download</a>
      <% } else { %> - <% } %></td>
  <td>
    <a href="<%=request.getContextPath()%>/tugas/edit?id=<%=t.getId()%>">Edit</a> |
    <a href="<%=request.getContextPath()%>/tugas/delete?id=<%=t.getId()%>" onclick="return confirm('Hapus?')">Hapus</a>
  </td>
</tr>
<% } %>
</table>
</body>
</html>

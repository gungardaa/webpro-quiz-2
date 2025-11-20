<%@ page import="java.util.List, model.MataKuliah" %>
<%@ page session="true" %>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath()+"/users/login.jsp"); return; }
    List<MataKuliah> list = (List<MataKuliah>) request.getAttribute("list");
%>
<html>
<head><title>Daftar Mata Kuliah</title></head>
<body>
<h2>Daftar Mata Kuliah</h2>
<p><a href="<%=request.getContextPath()%>/dashboard/dashboard.jsp">Dashboard</a> | <a href="<%=request.getContextPath()%>/matakuliah/add">Tambah</a></p>
<table border="1">
<tr><th>ID</th><th>Kode</th><th>Nama</th><th>Aksi</th></tr>
<% for (MataKuliah m : list) { %>
<tr>
  <td><%= m.getId() %></td>
  <td><%= m.getKodeMatkul() %></td>
  <td><%= m.getNamaMatkul() %></td>
  <td>
    <a href="<%=request.getContextPath()%>/matakuliah/edit?id=<%=m.getId()%>">Edit</a> |
    <a href="<%=request.getContextPath()%>/matakuliah/delete?id=<%=m.getId()%>" onclick="return confirm('Hapus?')">Hapus</a>
  </td>
</tr>
<% } %>
</table>
</body>
</html>

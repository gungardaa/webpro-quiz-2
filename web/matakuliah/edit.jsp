<%@ page import="model.MataKuliah" %>
<%@ page session="true" %>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath()+"/users/login.jsp"); return; }
    MataKuliah m = (MataKuliah) request.getAttribute("m");
%>
<html>
<head><title>Edit Mata Kuliah</title></head>
<body>
<h2>Edit Mata Kuliah</h2>
<form method="post" action="${pageContext.request.contextPath}/matakuliah/edit">
  <input type="hidden" name="id" value="<%= m.getId() %>" />
  Kode Matkul: <input type="text" name="kode_matkul" value="<%= m.getKodeMatkul() %>" required /><br/>
  Nama Matkul: <input type="text" name="nama_matkul" value="<%= m.getNamaMatkul() %>" required /><br/>
  <button type="submit">Update</button>
</form>
</body>
</html>

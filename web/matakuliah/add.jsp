<%@ page session="true" %>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath()+"/users/login.jsp"); return; }
%>
<html>
<head><title>Tambah Mata Kuliah</title></head>
<body>
<h2>Tambah Mata Kuliah</h2>
<form method="post" action="${pageContext.request.contextPath}/matakuliah/add">
  Kode Matkul: <input type="text" name="kode_matkul" required /><br/>
  Nama Matkul: <input type="text" name="nama_matkul" required /><br/>
  <button type="submit">Simpan</button>
</form>
</body>
</html>

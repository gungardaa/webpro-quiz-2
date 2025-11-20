<%@ page import="java.util.List, model.MataKuliah" %>
<%@ page session="true" %>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath()+"/users/login.jsp"); return; }
    List<MataKuliah> mks = (List<MataKuliah>) request.getAttribute("mks");
%>
<html>
<head><title>Tambah Tugas</title></head>
<body>
<h2>Tambah Tugas</h2>
<form method="post" action="${pageContext.request.contextPath}/tugas/add" enctype="multipart/form-data">
  Mata Kuliah:
  <select name="mata_kuliah_id" required>
    <% for (MataKuliah m : mks) { %>
      <option value="<%=m.getId()%>"><%=m.getNamaMatkul()%> (<%=m.getKodeMatkul()%>)</option>
    <% } %>
  </select><br/>
  Judul: <input type="text" name="judul_tugas" required /><br/>
  Deskripsi: <br/><textarea name="deskripsi"></textarea><br/>
  Deadline: <input type="datetime-local" name="deadline" /><br/>
  File (opsional): <input type="file" name="file" /><br/>
  <button type="submit">Simpan</button>
</form>
</body>
</html>

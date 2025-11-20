<%@ page import="model.Tugas, java.util.List, model.MataKuliah" %>
<%@ page session="true" %>
<%
    model.User user = (model.User) session.getAttribute("user");
    if (user == null) { response.sendRedirect(request.getContextPath()+"/users/login.jsp"); return; }
    Tugas t = (Tugas) request.getAttribute("t");
    List<MataKuliah> mks = (List<MataKuliah>) request.getAttribute("mks");
%>
<html>
<head><title>Edit Tugas</title></head>
<body>
<h2>Edit Tugas</h2>
<form method="post" action="${pageContext.request.contextPath}/tugas/edit" enctype="multipart/form-data">
  <input type="hidden" name="id" value="<%=t.getId()%>" />
  Mata Kuliah:
  <select name="mata_kuliah_id" required>
    <% for (MataKuliah m : mks) { %>
      <option value="<%=m.getId()%>" <%= m.getId()==t.getMataKuliahId() ? "selected":"" %>><%=m.getNamaMatkul()%></option>
    <% } %>
  </select><br/>
  Judul: <input type="text" name="judul_tugas" value="<%=t.getJudulTugas()%>" required /><br/>
  Deskripsi: <br/><textarea name="deskripsi"><%=t.getDeskripsi()%></textarea><br/>
  Deadline: <input type="datetime-local" name="deadline" value="<%= t.getDeadline()!=null ? t.getDeadline().toLocalDateTime().toString().replace(" ", "T"): "" %>" /><br/>
  Status:
  <select name="status">
    <option value="belum" <%= "belum".equals(t.getStatus())?"selected":""%>>Belum</option>
    <option value="selesai" <%= "selesai".equals(t.getStatus())?"selected":""%>>Selesai</option>
  </select><br/>
  Ganti file (opsional): <input type="file" name="file" /><br/>
  <button type="submit">Update</button>
</form>
</body>
</html>

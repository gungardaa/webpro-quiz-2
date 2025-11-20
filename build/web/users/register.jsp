<html>
<head><title>Register</title></head>
<body>
<h2>Register</h2>
<c:if test="${not empty error}">
  <p style="color:red">${error}</p>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/users/register">
  Nama: <input type="text" name="name" required /><br/>
  Email: <input type="email" name="email" required /><br/>
  Password: <input type="password" name="password" required /><br/>
  <button type="submit">Daftar</button>
</form>
</body>
</html>

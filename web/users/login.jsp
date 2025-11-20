<%@ page import="model.User" %>
<%@ page session="true" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Login</h2>
<c:if test="${not empty error}">
  <p style="color:red">${error}</p>
</c:if>
<form method="post" action="${pageContext.request.contextPath}/users/login">
  Email: <input type="email" name="email" required /><br/>
  Password: <input type="password" name="password" required /><br/>
  <button type="submit">Login</button>
</form>
<p><a href="${pageContext.request.contextPath}/users/register">Daftar</a></p>
</body>
</html>

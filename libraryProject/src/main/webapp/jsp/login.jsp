<%--
  Created by IntelliJ IDEA.
  User: danilzabinskij
  Date: 06.12.2024
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Регистрация</title>
</head>
<body>
<h2>Регистрация</h2>
<form action="register" method="post">
  <label for="username">Имя пользователя:</label>--%>
  <input type="text" name="username" required><br>

  <label for="password">Пароль:</label>
  <input type="password" name="password" required><br>

  <label for="email">Email:</label>
  <input type="email" name="email" required><br>

  <input type="submit" value="Зарегистрироваться">
</form>
<p>Уже есть аккаунт? <a href="login.jsp">Войти</a></p>
</body>
</html>

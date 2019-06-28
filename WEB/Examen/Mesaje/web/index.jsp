<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 22.06.2019
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>

<div class="container">
    <h1>Login</h1>
    <hr>
    <form action="/login" method="post">
        <label>Username</label><br>
        <input type="text" name='username' placeholder="Enter Username" required><br>
        <label>Password</label><br>
        <input type="password" name='password' placeholder="Enter Password" required>
        <br><br>
        <button type="submit">Login</button>
    </form>
    <hr>
</div>
<%session.setAttribute("userDest", null); %>
</body>
</html>

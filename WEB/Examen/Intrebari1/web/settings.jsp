<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 22.06.2019
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Settings</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/askstyle.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <script src="js/jquery.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Questions</a></li>
    <li><a href="/intreaba.jsp">Ask</a></li>
    <li><a href="/settings.jsp">Settings</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="container">
    <h1> Change avatar: </h1>
<form method="post" action="/uploadPhoto" enctype="multipart/form-data">
    <label>
        Upload file:<br>
    <input type="file" name="file" />
    </label>
    <button type="submit">Save</button>
</form>
    <br>
    <br>
    <h1> Change password: </h1>
    <form method="post" action="/schimbaParola">
        <label>
            Type new password:<br>
            <input type="password" name="password" />
        </label>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>

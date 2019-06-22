<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 30.05.2019
  Time: 12:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Regex</title>
</head>
<body>
<a href="index.jsp">Logout</a>
<form action="/addRegex" method="post">
    Enter regex : <input type="text" name="regex"> <BR>
    <input type="submit" value="Add"/>
</form>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fotografii</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Poze</a></li>
    <li><a href="/incarcarePoza.jsp">Incarcare</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="container">
    <textarea placeholder="Descriere" form="myform" name="descriere" cols="50" rows="5"></textarea>
    <form action="/incarcarePoza" method="post" enctype="multipart/form-data" id="myform">
        <input name = "imagine" type="file"><br>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 23.06.2019
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/askstyle.css">
    <script src="js/jquery.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAdmin.jsp">Produse</a></li>
    <li><a href="/adaugaProdus.jsp">Adauga</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="container">
    <h1>Ask a question</h1>
    <hr>

    <form id="myForm" method="post" action="/adaugaProdus" enctype="multipart/form-data">
        <label>
            Nume:<br>
            <input type="text" name="nume">
        </label>
        <label>
            Descriere: <br>
            <textarea id="descriere" name="descriere" rows="5" placeholder="Type here" form="myForm"></textarea>
        </label>
        <label>
            Producator:<br>
            <input type="text" name="producator">
        </label>
        <label>
            Pret:<br>
            <input type="text" name="pret">
        </label>
        <label>
            Cantitate:<br>
            <input type="text" name="cantitate">
        </label>
        <label>
            Imagine:<br>
            <input type="file" name="imagine">
        </label>
        <button id="save" type="submit">Save</button>
    </form>
    <hr>
</div>
</body>
</html>

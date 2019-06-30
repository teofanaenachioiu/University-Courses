<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 17:45
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
    <li><a href="/index.jsp">View</a></li>
    <li><a href="/creare.jsp">Creare</a></li>
</ul>

<div class="container">
    <h1>Adauga slide</h1>
    <hr>

    <form id="myForm" method="post" action="/adaugaSlide" enctype="multipart/form-data">
        <input required type="text" name="titlu" placeholder="Titlu"><br>
        <textarea required name="text" rows="5" placeholder="Text" form="myForm"></textarea><br>
        <input required type="number" name="nrSecunde" placeholder="nrSec"><br>
        <label>
            Imagine:<br>
            <input required type="file" name="imagine">
        </label><br>
        <label>
            Layout:<br>
            <input required type="radio" name="layout" value="layout1"> Layout 1<br>
            <input required type="radio" name="layout" value="layout2"> Layout 2<br>
        </label><br>
        <button id="save" type="submit">Save</button>
    </form>
    <hr>
</div>


</body>
</html>

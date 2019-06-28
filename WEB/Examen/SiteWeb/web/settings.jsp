<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Site web</title>

    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/meniu.css">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>


    <script src='js/spectrum.js'></script>
    <link rel='stylesheet' href='css/spectrum.css' />
    <script>
        function myDP() {
            $('#myDatePicker').datepicker({
                dateFormat: 'yy-mm-dd',
                inline: true,
                onSelect: function (d) {
                    console.log(d);
                    var date = $(this).datepicker('getDate');
                    alert(date.getTime());
                }
            });
        }

        $(document).ready(function () {
            myDP();
            $("#myColorPicker").spectrum({
                color: "#f00",
                preferredFormat: "hex",
                change: function(color) {
                    console.log(color.toHexString());
                }
            });

            $("#myColorPickerText").spectrum({
                color: "#f00",
                preferredFormat: "hex",
                change: function(color) {
                    console.log(color.toHexString());
                }
            });
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Users</a></li>
    <li><a href="/top.jsp">Top users</a></li>
    <li><a href="/settings.jsp">Settings</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="container">
    <h1> Setari</h1>

    <form action="/uploadPhoto" method="post" enctype="multipart/form-data">
        <label for="photo">Data</label><br>
        <input id="photo" type="file" name="path_avatar"><br>
        <button type="submit">Upload</button>
    </form>

    <label for="myText">
        Descriere </label><br>
    <textarea id="myText" cols="50" rows="5" name="descriere" form="myForm"></textarea>

    <form action="/setDetaliiUser" method="post" id="myForm">
        <label for="myDatePicker">Data</label><br>
        <input id="myDatePicker" type="date" name="datan"><br>
        <label for="myColorPicker">Coloare background</label><br>
        <input id="myColorPicker" value='hsv(71, 100%, 100%)' name="culoareBg" type="text" /><br>
        <label for="myColorPickerText">Coloare text</label><br>
        <input id="myColorPickerText" value='hsv(71, 100%, 100%)' name="culoareText" type="text" /><br>
        <button type="submit">Save</button>
    </form>
</div>
</body>
</html>

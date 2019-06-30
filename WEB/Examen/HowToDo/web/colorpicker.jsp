<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 28.06.2019
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Color Picker</title>
    <%--<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">--%>
    <%--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"--%>
          <%--integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">--%>

    <script src="js/jquery.js"></script>
    <%--<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>--%>


    <script src='js/spectrum.js'></script>
    <link rel='stylesheet' href='css/spectrum.css' />
    <script>
        $(document).ready(function () {
            $("#myColorPicker").spectrum({
                color: "#f00",
                preferredFormat: "hex",
                change: function(color) {
                    alert(color.toHexString());
                }

            });

        })
    </script>
</head>
<body>
<label for="myColorPicker">Coloare text</label><br>
<input id="myColorPicker" value='hsv(71, 100%, 100%)' name="culoareText" type="text" /><br>
</body>
</html>

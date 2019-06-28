<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 16:31
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
    <style>
        img{
            width: 200px;
        }
    </style>
    <script src="js/jquery.js"></script>
    <script src="js/sortTable.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script>
        function construiretabel(parse) {
            var continut = '<tr><th onclick="sortTable(0)">Nume</th><th>Poza</th><th>Descriere</th><th onclick="sortTable(1)">Medie</th><th onclick="sortTable(2)">DataNasterii</th></tr>';
            for (var i = 0; i < parse.length; i++) {
                var user = parse[i];
                var myDate = new Date(user.datan);
                continut += '<tr><td >' + user.username +
                    '</td><td><img src = "' + user.path_avatar + '"' +
                    '</td><td >' + user.descriere +
                    '</td><td >' + user.medie +
                    '</td><td >' + myDate +
                    '</td></tr>';
            }
            $("#myTable").html(continut);
        }

        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: '/getTop5Users',
                success: function (result) {
                    console.log(result);
                    construiretabel(JSON.parse(result));
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

<table id="myTable" class="table table-striped ">
</table>
</body>
</html>

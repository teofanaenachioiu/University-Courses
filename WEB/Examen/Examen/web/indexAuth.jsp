<%@ page import="webapp.model.Utilizator" %><%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 28.06.2019
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Examen</title>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/meniu.css">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        function menageriazaSesiunea(){
            $("#logout").click(function () {
                var user = "<%Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
                out.print(utilizator);%>";
                <%session.invalidate();%>
                console.log(user);
                window.location.href= "/index.jsp";
            })
        }

        function creareTabel(parse) {
            var continut = '<tr><th>Nume</th><th>Timp</th></tr>';
            for (var i = 0; i < parse.length; i++) {
                var user = parse[i];
                continut += '<tr><td >' + user.nume +
                    '</td><td >' + user.timp +
                    '</td></tr>';
            }
            $("#myTable").html(continut);

        }

        function getCastigatori() {
            $.ajax({
                type: 'GET',
                url: '/getCastigatori',
                success: function (result) {
                    creareTabel(JSON.parse(result));
                }
            });

        }

        $(document).ready(function () {
            menageriazaSesiunea();
            getCastigatori();
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Hall</a></li>
    <li><a href="/adaugaPoza.jsp">Adauga</a></li>
</ul>

<table class="table table-striped" id="myTable">

</table>

</body>
</html>
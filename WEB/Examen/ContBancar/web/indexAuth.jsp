<%@ page import="webapp.model.Utilizator" %><%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 25.06.2019
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestiune cont</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        <%
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        %>
        var username = "<% out.print(utilizator.getUsername());%>";

        function adaugaOptiuniInSelect(parse) {
            console.log(parse);
            var continut = '';
            for (var i = 0; i < parse.length; i++) {
                var utilizator = parse[i];
                continut += '<option value=' + utilizator.cont +
                    '>' + utilizator.nume +
                    '</option>';
            }
            $('#selectUtilizatori').html(continut);
        }

        function getUtilizatori() {
            $.ajax({
                type: 'POST',
                data: {username: username},
                url: '/getUtilizatori',
                success: function (result) {
                    adaugaOptiuniInSelect(JSON.parse(result));
                }
            });
        }

        $(document).ready(function () {
            getUtilizatori();
        })

    </script>

</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Cont</a></li>
    <li><a href="/istoric.jsp">Istoric</a></li>
    <li><a href="/index.jsp">Login</a></li>
</ul>
<h2>
    <%
        out.print("Utilizator: " + utilizator.getNume());
    %>
</h2>
<h3>
    <%
        out.print("Suma de bani: " + utilizator.getSuma() + " lei");
    %>
</h3>


<label for="selectUtilizatori">Utilizatori</label><br>
<select id="selectUtilizatori" name="utilizatori" form="myForm">
</select>
<form action="/transferaBani" method="post" id="myForm">
    <input type="number" name="suma" placeholder="Suma">
    <input value="Transfera" type="submit">
</form>
</body>
</html>

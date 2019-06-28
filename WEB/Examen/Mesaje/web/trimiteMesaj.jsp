<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 26.06.2019
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mesaje</title>
    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <link rel="stylesheet"
          href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script>
        var userDest =  "<% out.print(session.getAttribute("userDest")); %>";

        function adaugaOptiuni(parse) {
            var continut = '';
            for (var i = 0; i < parse.length; i++) {
                var dest = parse[i];
                if (dest == userDest) {
                    continut += '<option selected value = ' + dest + '>' + dest + '</option>';
                }
                else {
                    continut += '<option value = ' + dest + '>' + dest + '</option>';
                }

            }
            $("#destinatari").html(continut);
        }

        function getDestinatari() {
            $.ajax({
                type: 'GET',
                url: '/getDestinatari',
                success: function (result) {
                    adaugaOptiuni(JSON.parse(result));
                }
            });
        }

        $(document).ready(function () {
            getDestinatari();
            console.log(userDest);
        })
    </script>
</head>

<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Mesaje</a></li>
    <li><a href="/trimiteMesaj.jsp">Trimite</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="container">
    <label for="destinatari">Destinatar: </label><select required id="destinatari" name="userDest" form="myForm">
</select>
    <form id="myForm" action="/trimite" method="post">
        <input required type="text" name="subiect" placeholder="Subiect">
        <textarea name="continut" form="myForm" placeholder="Continut"></textarea>
        <br><br>
        <button type="submit">Send</button>
    </form>

</div>
</body>
</html>

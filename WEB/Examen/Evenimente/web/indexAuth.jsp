<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 26.06.2019
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add event</title>
    <%--<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">--%>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <%--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"--%>
          <%--integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">--%>
    <script src="js/jquery.js"></script>
    <%--<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>--%>

    <%--<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>--%>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
    <script>
        $( function() {
            $( "#datepicker" ).datepicker();
        } );
        $('#timepicker').timepicker({
            timeFormat: 'hh:mm p',
            interval: 60,
            minTime: '10',
            maxTime: '6:00pm',
            defaultTime: '11',
            startTime: '10:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true
        });

        $(document).ready(function(){
            $('input#timepicker').timepicker({});
        });
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="container">
    <h1>Adauga eveniment</h1>
    <form method="post" action="/adauga">
        <label for="datepicker">Data:</label><input name="dataEv" type="text" id="datepicker">
        <label for="timepicker">Ora:</label><input name="oraEv" type="text" id="timepicker">
        <label for="descriere">Descriere:</label><input type="text" id="descriere" name="descriere">
        <button type="submit">Adauga</button>
    </form>
</div>
</body>
</html>

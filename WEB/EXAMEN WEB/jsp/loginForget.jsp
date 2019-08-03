<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 22.06.2019
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script>
        function myDP (){
            $('#myDatePicker').datepicker({
                dateFormat: 'yy-mm-dd',
                inline: true,
                onSelect: function(d) {
                    console.log(d);
                    var date = $(this).datepicker('getDate');
                    alert(date.getTime());
                }
            });
        }

        $(document).ready(function () {
            $('#uitatParola').change(function() {
                if ($("#uitatParola").prop('checked'))
                    $("#resetareDiv").show();  // checked
                else
                    $("#resetareDiv").hide();
            });
            myDP();
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/index.jsp">Users</a></li>
    <li><a href="/inregistare.jsp">Sign up</a></li>
    <li><a href="/login.jsp">Login</a></li>
</ul>
<div class="container">
    <h1>Login</h1>
    <hr>
    <form action="/login" method="post">
        <label>Username</label><br>
        <input type="text" name='username' placeholder="Enter Username" required><br>
        <label>Password</label><br>
        <input type="password" name='password' placeholder="Enter Password" required>
        <br><br>
        <button type="submit">Login</button>
    </form>
    <input type="checkbox" id="uitatParola"> Am uitat parola <br>
    <hr>
</div>

<div id="resetareDiv" class="container" style="display:none;">
    <h1>Resetare parola</h1>
    <hr>
    <form action="/resetareParola" method="post">
        <label>Username</label><br>
        <input type="text" name='username' placeholder="Enter Username" required><br>

        <label for="myDatePicker">Data</label><br>
        <input id="myDatePicker" type="date" name="datan"><br>

        <label>Noua parola</label><br>
        <input type="password" name='password' placeholder="Enter Password" required><br>
        <br><br>
        <button type="submit">Change</button>
    </form>
    <hr>
</div>

</body>
</html>

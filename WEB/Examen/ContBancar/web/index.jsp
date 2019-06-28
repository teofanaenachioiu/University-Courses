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
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">

    <link rel="stylesheet" type="text/css" href="css/login.css">

    <script>
        function countul(penalizare) {
            var countDownDate = new Date().getTime() + penalizare * 1000;
            $("input").attr("disabled",true);
            var x = setInterval(function () {

                var demooo = $("#demo");
                var now = new Date().getTime();

                var distance = countDownDate - now;

                var days = Math.floor(distance / (1000 * 60 * 60 * 24));
                var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                demooo.html("Reluare logare in: " + days + "d " + hours + "h "
                    + minutes + "m " + seconds + "s ");

                if (distance < 0) {
                    clearInterval(x);
                    $("input").removeAttr("disabled");
                    demooo.html("");
                }
            }, 1000);
        }

        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: '/getPanelizare',
                success: function (result) {
                    if(result>0) {
                        countul(result);
                    }
                }
            });

        })

    </script>
</head>
<body>
<div class="container">
    <h1>Login</h1>
    <hr>
    <form method="post" action="/login">
        <label>Username</label><br>
        <input type="text" name='username' placeholder="Enter Username" required><br>
        <label>Password</label><br>
        <input type="password" name='password' placeholder="Enter Password" required>
        <br><br>
        <button id="save" type="submit">Login</button>
    </form>
    <hr>
    <p id="demo"></p>
</div>


</body>
</html>

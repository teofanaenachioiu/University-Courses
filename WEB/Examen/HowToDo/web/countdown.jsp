<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 28.06.2019
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.js"></script>

    <script>
        var countDownDate = new Date("Jan 5, 2021 15:37:25").getTime();

        var x = setInterval(function() {

            var now = new Date().getTime();

            var distance = countDownDate - now;

            var days = Math.floor(distance / (1000 * 60 * 60 * 24));
            var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            var seconds = Math.floor((distance % (1000 * 60)) / 1000);

            $("#demo").html(days + "d " + hours + "h " + minutes + "m " + seconds + "s ");

            if (distance < 0) {
                clearInterval(x);
                $("#demo").html("Gata");
            }
        }, 1000);
    </script>
</head>
<body>
<p id="demo"></p>
</body>
</html>

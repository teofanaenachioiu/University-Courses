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
    <link rel="stylesheet" type="text/css" href="../css/login.css">
    <link rel="stylesheet" type="text/css" href="../css/meniu.css">

    <script src="../js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        function adaugaUser(){
            var username = $("#username").val();
            var password = $("#password").val();
            var repassword = $("#repassword").val();
            $.ajax({
                type: 'POST',
                data: {username: username, password: password, repassword: repassword},
                url: '/adaugaUser',
                success: function (result) {
                }
            });

        }
        $(document).ready(function () {
            $("#save").click(function () {
                var pass = $("#password").val();
                var repass = $("#repassword").val();
                console.log(pass);
                console.log(repass);
                if (pass != repass) {
                    $("#gresit").removeAttr("hidden");
                }
                else {

                    $("#gresit").attr("hidden", true);
                }
                adaugaUser();
            })
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/index.jsp">Users</a></li>
    <li><a href="/jsp/inregistare.jsp">Sign up</a></li>
    <li><a href="/login.jsp">Login</a></li>
</ul>
<div class="container">
    <h1>Sign up</h1>
    <hr>
    <form>
        <label>Username</label><br>
        <input type="text" id='username' placeholder="Enter Username" required><br>
        <label>Password</label><br>
        <input type="password" id='password' placeholder="Enter Password" required><br>
        <label>Re Password</label><br>
        <input type="password" id='repassword' placeholder="Enter Re Password" required>
    </form>
    <button id="save">Sign up</button>
    <label id="gresit" hidden>Parola invalida</label>
    <hr>
</div>
</body>
</html>

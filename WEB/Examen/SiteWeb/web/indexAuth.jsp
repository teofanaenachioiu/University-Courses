<%@ page import="webapp.model.Utilizator" %><%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 12:44
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
    <link rel="stylesheet" type="text/css" href="css/listastyle.css">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .checked {
            color: orange;
        }
    </style>
    <script>
        var utilizatori;
        var username = "<% Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        out.print(utilizator.getUsername());
        %>";
        function getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') {
                    c = c.substring(1);
                }
                if (c.indexOf(name) == 0) {
                    return c.substring(name.length, c.length);
                }
            }
            return "";
        }

        function checkCookie() {
            var bgCol = getCookie("culoareBg"+username);
            var textCol = getCookie("culoareText"+username);
            if (bgCol !== "") {
                $("body").css('background-color', bgCol).css('color', textCol);
            }
        }

        function construireLista() {
            var continut ='';
            for(var i=0; i<utilizatori.length;i++){
                var utilizator = utilizatori[i];
                continut += '<div class="produs" id = '+utilizator.username+'>' +
                    '<img src="'+utilizator.path_avatar+'"><p>'+
                    utilizator.username +'</p>' +
                    '<span class="fa fa-star" id = "1"></span>\n' +
                    '<span class="fa fa-star" id = "2"></span>\n' +
                    '<span class="fa fa-star" id = "3"></span>\n' +
                    '<span class="fa fa-star" id = "4"></span>\n' +
                    '<span class="fa fa-star" id = "5"></span>' +
                    ' </div>'
            }
            return continut;

        }

        function adaugaRating(id, idParinte) {
            $.ajax({
                type: 'POST',
                data: {nota: id, username: idParinte},
                url: '/adaugaNota',
                success: function (result) {
                }
            });
        }

        function  evenimente() {
            $(".continut").html(construireLista());
            $(".fa.fa-star").on("click", function () {
                var id = $(this).attr("id");
                var idParinte = $(this).parent().attr("id");
                adaugaRating(id, idParinte);

            }).on("hover", function () {
                var id = $(this).attr("id");
                for(var i=1;i<=5;i++){
                    if(i<=id){
                        document.getElementById(i).classList.add("checked");
                    }
                    else {
                        document.getElementById(i).classList.remove("checked");
                    }
                }
            });
        }

        function getUtilizatori() {
            $.ajax({
                type: 'GET',
                data: {username: username},
                url: '/getAltiUtilizatori',
                success: function (result) {
                    utilizatori =JSON.parse(result) ;
                    evenimente();
                }
            });
        }

        $(document).ready(function () {
            checkCookie();
            getUtilizatori();
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
<div class="continut">

</div>
</body>
</html>

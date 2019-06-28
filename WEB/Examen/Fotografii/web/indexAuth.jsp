<%@ page import="webapp.model.Utilizator" %><%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fotografii</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/thumb.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="js/jquery.js"></script>
    <%--<script src="js/thumb.js"></script>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        var username = "<%Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        out.print(utilizator.getUsername());
        %>";

        function adaugarePoze() {
            if(username=="admin"){
                $("#ptUser").attr("hidden",true);
                $.ajax({
                    type: 'GET',
                    url: '/getIncarcariAll',
                    success: function (result) {
                        var continut = creareThumb(JSON.parse(result));
                        $("#continut").html(continut);
                        thumbbbEvent();
                    }
                });
            }
            else {
                $.ajax({
                    type: 'GET',
                    data: {username: username},
                    url: '/getIncarcariUser',
                    success: function (result) {
                        var continut = creareThumb(JSON.parse(result));
                        $("#continut").html(continut);
                        thumbbbEvent();
                    }
                });
            }
        }

        function thumbbbEvent() {
            $(".closeBtn").on("click",function () {
                var idPoza = $(this).attr("id");
                $.ajax({
                    type: 'POST',
                    data: {id: idPoza},
                    url: '/deletePoza',
                    success: function (result) {
                        adaugarePoze();
                    }
                });
            })
        }

        function creareThumb(parse) {
            var continut = '';
            for (var i = 0 ;i<parse.length; i++){
                var incarcare = parse[i];
                continut+='<div class="content">' +
                    '' +
                    '<div class="image"><a target="_blank" href="' +incarcare.path_poza+ '">' +
                    '<img src="' + incarcare.path_poza + '" alt="Poza">\n' +
                    '</a><button class="closeBtn" id="' + incarcare.id +
                    '">X</button></div>' +
                    '<div class="text"><p>' + incarcare.descriere +
                    '</p></div>' +
                    '</div>' +
                    '<br>';
            }
            return continut;
        }

        $(document).ready(function () {
            adaugarePoze();

        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Produse</a></li>
    <li id="ptUser"><a href="/incarcarePoza.jsp">Incarcare</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div id="continut"></div>
</body>
</html>

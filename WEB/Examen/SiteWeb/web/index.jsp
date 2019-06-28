<%--
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



    <script>
        function construireLista(parse) {
            var continut ='';
            for(var i=0; i<parse.length;i++){
                var utilizator = parse[i];
                continut += '<div class="produs">' +
                    '<img src="'+utilizator.path_avatar+'">'+
                    utilizator.username +' </div>'
            }
            $(".continut").html(continut);
        }

        function getUtilizatori() {
            $.ajax({
                type: 'GET',
                url: '/getUtilizatori',
                success: function (result) {
                    construireLista(JSON.parse(result));
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
    <li><a href="/index.jsp">Users</a></li>
    <li><a href="/inregistare.jsp">Sign up</a></li>
    <li><a href="/login.jsp">Login</a></li>
  </ul>
  <div class="continut">

  </div>
  </body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fotografii</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/searchBar.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/thumb.css">
    <script src="js/thumb.js"></script>
    <script>
        function adaugarePoze() {
            var key = "<% String key = (String) session.getAttribute("keyword");
            out.print(key);%>";
            console.log(key);
            $.ajax({
                type: 'GET',
                data: {key: key},
                url: '/getIncarcariKey',
                success: function (result) {
                    var continut = creareThumb(JSON.parse(result));
                    $("#continut").html(continut);
                }
            });
        }

        $(document).ready(function () {
            adaugarePoze();
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/index.jsp">Back</a></li>
</ul>
<div id="continut"></div>
</body>
</html>

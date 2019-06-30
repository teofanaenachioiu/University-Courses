<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 29.06.2019
  Time: 08:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Examen</title>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="css/meniu.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/meniu.css">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        
        $(document).ready(function () {
            $("#jocNou").click(function () {
                window.location.href="/index.jsp";
            });
            var timp = "<%session.getAttribute("timp");%>";
            console.log(timp);
            alert(timp);
            if(timp!="0"){
                $("#utilizator").show();
            }
            else {
                $("#utilizator").hide();
            }
            $("#myBtn").click(function () {
                var nume = $("#nume").val();
                $.ajax({
                    type: 'POST',
                    data: {nume: nume},
                    url: '/adaugaNumele',
                    success: function (result) {
                        $("#utilizator").hide();
                    }
                });

            });
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Hall</a></li>
    <li><a href="/adaugaPoza.jsp">Adauga</a></li>
</ul>
<button id="jocNou">Joc Nou</button>
<div class="container" id="utilizator" style="display: none">
    <h1>Numele</h1>
    <hr>
    <form id="morrr" method="post" action="/adaugaNumele">
        <label>
            Numele: <br>
            <textarea id="nume" name="nume" rows="5" placeholder="Type here" form="morrr"></textarea>
        </label><br>
       
    </form>
    <button id="myBtn" type="submit">Adauga</button>
    <hr>
</div>

<div class="container">
    <h1>Adauga poza</h1>
    <hr>
    <form id="myForm" method="post" action="/adaugaImagine" enctype="multipart/form-data">
        <label>
            Descriere: <br>
            <textarea id="descriere" name="descriere" rows="5" placeholder="Type here" form="myForm"></textarea>
        </label><br>
        <label>
            Imagine:<br>
            <input type="file" name="imagine">
        </label><br>
        <button id="save" type="submit">Save</button>
    </form>
    <hr>
</div>



</body>
</html>

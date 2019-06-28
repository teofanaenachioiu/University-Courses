<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 23.06.2019
  Time: 00:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Intreaba</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/askstyle.css">
    <script src="js/jquery.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>

        $(document).ready(function () {
            var mesaj = $("#comment").text;
            $("#save").click(function () {
                $.ajax({
                    type: 'POST',
                    data:{mesaj: mesaj},
                    url: '/intrebare',
                    success: function (result) {
                        console.log(result);
                    },
                    error: function (result) {
                        console.log(result);
                    }
                });
            });
        });

        // var xmlhttp;
        //
        // function submitFunction() {
        //     alert("aici");
        //     xmlhttp = GetXmlHttpObject();
        //     if (xmlhttp == null) {
        //         alert("Your browser does not support XMLHTTP!");
        //         return;
        //     }
        //     var url = "/intrebare";
        //     var comment = document.getElementById("comment").value;
        //     var Params = "mesaj=" + comment;
        //     alert(Params);
        //     xmlhttp.onreadystatechange = stateChangedBookUpdated;
        //     xmlhttp.open("POST", url, true);
        //     //xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //     xmlhttp.send(Params);
        //     alert("Am trimis intrebarea");
        // }
        //
        // function stateChangedBookUpdated() {
        //     if (xmlhttp.readyState == 4) {
        //         alert(xmlhttp.responseText);
        //     }
        // }
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Questions</a></li>
    <li><a href="/intreaba.jsp">Ask</a></li>
    <li><a href="/settings.jsp">Settings</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="container">
    <h1>Ask a question</h1>
    <hr>
    <textarea id="comment" name="mesaj" rows="5" placeholder="Type here"></textarea>
    <br><br>

    <button  id="save" type="submit">Ask</button>
    <hr>
</div>
</body>
</html>

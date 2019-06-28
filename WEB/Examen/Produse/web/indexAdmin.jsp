<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 23.06.2019
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/mystyle.css">
    <script src="js/jquery.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            getProduse();
        });
        var xmlhttp;

        function getProduse() {
            xmlhttp = GetXmlHttpObject();
            if (xmlhttp == null) {
                alert("Your browser does not support XMLHTTP!");
                return;
            }
            var url = "/viewProduse";
            xmlhttp.onreadystatechange = stateChanged;
            xmlhttp.open("GET", url, true);
            xmlhttp.send(null);
        }

        function deleteItem(id) {
            xmlhttp = GetXmlHttpObject();
            if (xmlhttp == null) {
                alert("Your browser does not support XMLHTTP!");
                return;
            }
            var url = "/stergeProdus";
            // url=url+"?id="+id;
            xmlhttp.onreadystatechange = stateChangedRemoveItem;
            xmlhttp.open("POST", url, true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send("id="+id);
        }

        function seteazaContinut(result) {
            var rez ='';
            for(var i=0;i<result.length;i++){
                elem = result[i];
                var continut =  '<div class="produs">' +
                    '<p>'+elem.nume+'</p>' +
                        '<button onclick="deleteItem(elem.id)">Delete</button>'+
                    '</div>';
                rez =rez+continut;
            }
            return rez;

        }

        function stateChanged() {
            if (xmlhttp.readyState === 4) {
                var result = JSON.parse(xmlhttp.responseText);
                document.getElementById("aici").innerHTML = seteazaContinut(result);
            }
        }

        function stateChangedRemoveItem() {
            if (xmlhttp.readyState === 4) {
               getProduse();
            }
        }

        function GetXmlHttpObject() {
            if (window.XMLHttpRequest) {        // code for IE7+, Firefox, Chrome, Opera, Safari
                return new XMLHttpRequest();
            }
            if (window.ActiveXObject) {         // code for IE6, IE5
                return new ActiveXObject("Microsoft.XMLHTTP");
            }
            return null;
        }

    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAdmin.jsp">Produse</a></li>
    <li><a href="/adaugaProdus.jsp">Adauga</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<div class="continut" id ="aici">
    <p>Daaaaa</p>
</div>
</body>
</html>

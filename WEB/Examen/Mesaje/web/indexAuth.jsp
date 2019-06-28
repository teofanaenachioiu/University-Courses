<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 26.06.2019
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mesaje</title>
    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/myStyle.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script>
        function contstuireTabel(result) {
            var continut = '';
            for (var i = 0; i < result.length; i++) {
                var mesaj = result[i];
                var dataMesaj = new Date(mesaj.dataExp);
                if (mesaj.citit) {
                    continut += '<tr id = ' + mesaj.id + '>';
                }
                else {
                    continut += '<tr id =' + mesaj.id + ' class = "necitit">'
                }
                continut +=
                    '<td hidden>' + mesaj.id + '</td>' +
                    '<td class="myItem">' + mesaj.userExp + '</td>' +
                    '<td class="myItem">' + dataMesaj.getDate() + '/' + (dataMesaj.getMonth() + 1) + '/' + dataMesaj.getFullYear() + '</td>' +
                    '<td class="myItem">' + mesaj.subiect + '</td>' +
                    '<td class="deleteItem" id = ' + mesaj.id + '> <i class="fa fa-trash" aria-hidden="true"></i></td>' +
                    '</tr>'
            }
            $("#myTable").append(continut);
        }

        var pagina = 0;

        function loadMesaje() {
            $.ajax({
                type: 'GET',
                data: {nrPag: pagina},
                url: '/getMesajePrimite',
                success: function (result) {
                    contstuireTabel(JSON.parse(result));
                    pagina = pagina + 1;
                }
            });
        }

        function getContinutMesaj(idMesaj) {
            $.ajax({
                type: 'GET',
                data: {idMesaj: idMesaj},
                url: "/getContinutMesaj",
                success: function (result) {
                    $("#myContent").val(result).removeAttr("hidden");
                    $("#replyBtn").removeAttr("hidden");
                    $("#myLabel").removeAttr("hidden");

                }
            });
        }

        function deleteItem(id) {
            $.ajax({
                type: 'POST',
                data: {idMesaj: id},
                url: "/deleteMesaj",
                success: function () {
                    pagina = 0;
                    $("#myContent").attr("hidden", true);
                    $("#replyBtn").attr("hidden", true);
                    $("#myLabel").attr("hidden", true);
                    $("#myTable").html("<tr>\n" +
                        "        <th hidden>Id</th>\n" +
                        "        <th>Expeditor</th>\n" +
                        "        <th>Data</th>\n" +
                        "        <th>Subiect</th>\n" +
                        "    </tr>");
                    loadMesaje();

                }
            });
        }
        var userDest = '';
        $(document).ready(function () {
            loadMesaje();
            $("#loadMore").click(function () {
                loadMesaje();
            });
            $("#myTable").on("click", "tr", function () {
                var idMesaj = $(this).find('td:first').text();
                $(this).removeClass("necitit");
                getContinutMesaj(idMesaj);
            });
            $("#myTable").on("click", ".deleteItem", function () {
                var id = $(this).attr("id");
                deleteItem(id);
            });
            $("#replyBtn").click(function () {
                $.ajax({
                    type: 'GET',
                    url: "/redirectare",
                    success: function (result) {
                    }
                });
            })
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Mesaje</a></li>
    <li><a href="/trimiteMesaj.jsp">Trimite</a></li>
    <li><a href="/index.jsp">Logout</a></li>
</ul>
<button id="loadMore">Load More</button>
<br>
<table id="myTable">
    <tr>
        <th hidden>Id</th>
        <th>Expeditor</th>
        <th>Data</th>
        <th>Subiect</th>
    </tr>
</table>
<br>
<label hidden id="myLabel" for="myContent"> Continut mesaj: </label><br>
<textarea disabled hidden id="myContent" rows="7" cols="50"></textarea>

<a href="trimiteMesaj.jsp"><button hidden id="replyBtn"><i class="fa fa-reply"></i></button>
</a>
</body>
</html>

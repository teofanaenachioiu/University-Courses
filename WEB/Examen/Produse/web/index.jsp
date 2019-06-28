<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 23.06.2019
  Time: 12:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Produse</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/searchBar.css">
    <link rel="stylesheet" type="text/css" href="css/mystyle.css">
    <link rel="stylesheet" type="text/css" href="modal/modal.css">
    <script src="js/jquery.js"></script>
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>

        var rezultate;

        function inchideModalul() {
            document.getElementById("myModal").style.display = "none"
        }

        function adaugaViewNou(id) {
            $.ajax({
                type: 'GET',
                data: {id: id},
                url: '/adaugaViewNou',
                success: function (result) {
                }
            });
        }


        var getData = function (resultParsat) {
            var myData = [
                {x: 1, y: 0},
                {x: 2, y: 0},
                {x: 3, y: 0},
                {x: 4, y: 0},
                {x: 5, y: 0},
                {x: 6, y: 0},
                {x: 7, y: 0},
                {x: 8, y: 0},
                {x: 9, y: 0},
                {x: 10, y: 0},
                {x: 11, y: 0},
                {x: 12, y: 0}
            ];
            for(var i=0;i<resultParsat.length;i++){
                var element =resultParsat[i];
                myData[element.nrLuna - 1].y = element.nrViews;
            }
            return myData;
        };

        function myChart(idProdus) {
            $.ajax({
                type: 'GET',
                data: {idProdus: idProdus},
                url: '/popularitate',
                success: function (result) {
                    var resultParsat = JSON.parse(result);
                    var myData = getData(resultParsat);
                    var chart = new CanvasJS.Chart("chartContainer", {
                        animationEnabled: true,
                        title: {
                            text: "Popularity of product"
                        },
                        axisX: {
                            title: "Months"
                        },
                        axisY: {
                            title: "Number of views"
                        },
                        data: [{
                            type: "line",
                            name: "CPU Utilization",
                            connectNullData: true,
                            dataPoints: myData
                        }]
                    });
                    chart.render();
                }
            });
        }

        function deschideModalul(i) {
            document.getElementById("myModal").style.display = "block";
            var elem = rezultate[i];
            adaugaViewNou(elem.id);
            var continut = '<div>' +
                ' <p> Nume: ' + elem.nume + '</p>' +
                ' <p> Descriere: ' + elem.descriere + '</p>' +
                ' <p> Producator: ' + elem.producator + '</p>' +
                ' <p> Pret: ' + elem.pret + '</p>' +
                ' <p> Cantitate: ' + elem.cantitate + '</p>' +
                '<span class="asta"><img id = "pozaaa" src ="' + elem.path_poza + '">' +
                '<span class="close">&times;</span></span>' +
                '</div>' +
                '<div id="chartContainer" style="height: 300px; width: 100%;"></div>' +
                '<br><br><br><br><br>';
            document.getElementById("myDetails").innerHTML = continut;
            myChart(elem.id);
        }

        function seteazaContinut(result) {
            var rez = '';
            for (var i = 0; i < result.length; i++) {
                var elem = result[i];
                var continut = '<div class="produs" onclick=deschideModalul(' + i + ') >' +
                    '<p>' + elem.nume + '</p>' +
                    '<a target="_blank" href="' + elem.path_poza + '">\n' +
                    '  <img src="' + elem.path_poza + '" alt="Poza" style="width:100px">\n' +
                    '</a>' +
                    '</div>';
                rez = rez + continut;
            }
            return rez;
        }

        function seteazaContinutDetalii(result) {
            var rez = '';
            for (var i = 0; i < result.length; i++) {
                elem = result[i];
                var continut = '<div>' +
                    '<p>' + elem.nume + '</p>' +
                    '  <img src="' + elem.path_poza + '" alt="Poza" style="width:100px">\n' +
                    '</div>';
                rez = rez + continut;
            }
            return rez;
        }


        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: '/viewProduse',
                success: function (result) {
                    var resultParasat = JSON.parse(result);
                    rezultate = resultParasat;
                    $('#aici').html(seteazaContinut(resultParasat));
                }
            });

            document.getElementById("searchbar").oninput = function () {
                var valoare = this.value;
                console.log(valoare);
                $.ajax({
                    type: 'GET',
                    data: {filtru: valoare},
                    url: '/viewProduse',
                    success: function (result) {
                        var resultParasat = JSON.parse(result);
                        $('#aici').html(seteazaContinut(resultParasat));
                    }
                });
            };

            window.onclick = function (event) {
                var modal = document.getElementById("myModal");
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            }
        });
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/index.jsp">Produse</a></li>
    <li><a href="/login.jsp">Login</a></li>
</ul>
<div class="topnav">
    <input id="searchbar" name="filtru" type="text" placeholder="Search..."></div>

<div class="continut" id="aici">
</div>

<!-- The Modal -->
<div id="myModal" class="modal">
    <!-- Modal content -->
    <div id="myDetails" class="modal-content" onclick="inchideModalul()">
        <%--<span class="close">&times;</span>--%>
        <%--<p id="myDetails">Some text in the Modal..</p>--%>
    </div>
</div>


</body>
</html>

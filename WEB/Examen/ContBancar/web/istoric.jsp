<%@ page import="webapp.model.Utilizator" %><%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 25.06.2019
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Istoric</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="js/jquery.js"></script>
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <%--<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>--%>

    <script>
        <%
       Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
       %>
        var cont = "<%out.print(utilizator.getCont());%>";

        function populeazaTabela(parse) {
            var continut = '<tr><th onclick="sortTable(0)">id</th>' +
                '<th onclick="sortTable(1)">contExp</th>' +
                '<th onclick="sortTable(2)">contDest</th>' +
                '<th onclick="sortTable(3)">suma</th>' +
                '<th onclick="sortTable(4)">data</th></tr>';
            for (var i = 0; i < parse.length; i++) {
                var tranzactie = parse[i];
                var dataora = new Date(tranzactie.dataora);
                console.log(dataora);
                continut += '<tr>' +
                    '<td> ' + tranzactie.id + '</td>' +
                    '<td> ' + tranzactie.contExp + '</td>' +
                    '<td> ' + tranzactie.contDest + '</td>' +
                    '<td> ' + tranzactie.suma + '</td>' +
                    '<td> ' + dataora + '</td>' +
                    '</tr>'
            }
            $("#hisTable").html(continut);
        }


        function deseneazaGrafic(result) {
            var data = [];
            for (var i = 0; i < result.length; i++) {
                var el = result[i];
                data.push({x: el.id, y: el.suma});
            }
            var chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                title: {
                    text: "Cuantum tranzactii"
                },
                axisX: {
                    title: "Tranzactie"
                },
                axisY: {
                    title: "Suma"
                },
                data: [{
                    type: "line",
                    name: "CPU Utilization",
                    dataPoints: data
                }]
            });
            chart.render();

        }

        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                data: {cont: cont},
                url: '/getTranzactii',
                success: function (result) {
                    console.log("AICI");
                    console.log(result);
                    populeazaTabela(JSON.parse(result));
                    deseneazaGrafic(JSON.parse(result));
                }
            });


        });

    </script>
    <script>
        function sortTable(n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("hisTable");
            switching = true;
            // Set the sorting direction to ascending:
            dir = "asc";
            /* Make a loop that will continue until
            no switching has been done: */
            while (switching) {
                // Start by saying: no switching is done:
                switching = false;
                rows = table.rows;
                /* Loop through all table rows (except the
                first, which contains table headers): */
                for (i = 1; i < (rows.length - 1); i++) {
                    // Start by saying there should be no switching:
                    shouldSwitch = false;
                    /* Get the two elements you want to compare,
                    one from current row and one from the next: */
                    x = rows[i].getElementsByTagName("TD")[n];
                    y = rows[i + 1].getElementsByTagName("TD")[n];
                    /* Check if the two rows should switch place,
                    based on the direction, asc or desc: */
                    if (dir === "asc") {
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            // If so, mark as a switch and break the loop:
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir === "desc") {
                        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                            // If so, mark as a switch and break the loop:
                            shouldSwitch = true;
                            break;
                        }
                    }
                }
                if (shouldSwitch) {
                    /* If a switch has been marked, make the switch
                    and mark that a switch has been done: */
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    switching = true;
                    // Each time a switch is done, increase this count by 1:
                    switchcount++;
                } else {
                    /* If no switching has been done AND the direction is "asc",
                    set the direction to "desc" and run the while loop again. */
                    if (switchcount === 0 && dir === "asc") {
                        dir = "desc";
                        switching = true;
                    }
                }
            }
        }
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/indexAuth.jsp">Cont</a></li>
    <li><a href="/istoric.jsp">Istoric</a></li>
    <li><a href="/index.jsp">Login</a></li>
</ul>

<table class="table table-striped" id="hisTable"></table>
<div id="chartContainer" style="height: 300px; width: 100%;"></div>

</body>
</html>

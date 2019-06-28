<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 26.06.2019
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Evenimente</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/tabstyle.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        function sortTable(n) {
            var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
            table = document.getElementById("myTable2");
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
                    if (dir == "asc") {
                        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                            // If so, mark as a switch and break the loop:
                            shouldSwitch = true;
                            break;
                        }
                    } else if (dir == "desc") {
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
                    switchcount ++;
                } else {
                    /* If no switching has been done AND the direction is "asc",
                    set the direction to "desc" and run the while loop again. */
                    if (switchcount == 0 && dir == "asc") {
                        dir = "desc";
                        switching = true;
                    }
                }
            }
        }
    </script>
    <script>
        function openCity(evt, cityName) {
            console.log(cityName);
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(cityName).style.display = "block";
            if (document.getElementById(cityName).innerHTML === "") {
                getContinut(cityName);
            }
            evt.currentTarget.className += " active";
        }

    </script>
    <script>
        var gasit = false;
    6.n construiesteTaburi(parse) {
            var continut = '';
            var continutDupa = '';
            for (var i = 0; i < parse.length; i++) {
                var dataEv = parse[i];
                console.log(dataEv);
                var myData = new Date(dataEv);
                var format = myData.getDate() + '-'+ (myData.getMonth()+1) + '-' + myData.getFullYear();
                console.log(format);
                if(dataEv> new Date().getTime() && !gasit){
                    console.log("aiciiiiiiiiii");
                    gasit =true;
                    continut += ' <button id="defaultOpen" class="tablinks" onclick=openCity(event,"' + dataEv + '")>' + format + '</button>';
                }
                else {
                    continut += ' <button class="tablinks" onclick=openCity(event,"' + dataEv + '")>' + format + '</button>';
                }
                continutDupa += ' <div id=' + dataEv + ' class="tabcontent"></div>';
            }
            $(".tab").html(continut);
            $(".dinTab").append(continutDupa);
            document.getElementById("defaultOpen").click();
        }

        function getDate() {
            $.ajax({
                type: 'GET',
                url: '/getDate',
                success: function (result) {
                    construiesteTaburi(JSON.parse(result));
                }
            });
        }

        function construiesteTabel(dataEv,parse) {
            var continut = '<table id="myTable2"><tr>' +
                '<th >ID</th><th>Data</th><th onclick="sortTable(0)">Ora</th><th onclick="sortTable(1)">Descriere</th></tr> ';
            for (var i = 0; i < parse.length; i++) {
                var eve = parse[i];
                var myDate= new Date(eve.dataEv);
                var myHour= new Date(eve.oraEv);
                var formatData = myDate.getFullYear()+'-'+(myDate.getMonth()+1)+'-'+myDate.getDate();
                var formatOra = myHour.getHours()+":"+myHour.getMinutes();
                continut +=
                    '<tr><td>'+eve.id+'</td>' +
                    '<td>'+formatData+'</td>' +
                    '<td>'+formatOra+'</td>' +
                    '<td>'+eve.descriere+'</td>' +
                    '</tr>';

            }
            continut+= '</table>';
            document.getElementById(dataEv).innerHTML = continut;
        }

        function getContinut(dataEv) {
            $.ajax({
                type: 'GET',
                data: {dataEv: dataEv},
                url: '/getContinut',
                success: function (result) {
                    construiesteTabel(dataEv,JSON.parse(result));
                }
            });
        }

        $(document).ready(function () {
            getDate();
        })
    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/index.jsp">Evenimente</a></li>
    <li><a href="/login.jsp">Login</a></li>
</ul>
<div class="dinTab">
    <div class="tab">
    </div>
</div>

</body>
</html>

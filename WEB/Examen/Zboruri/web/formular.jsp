<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 24.06.2019
  Time: 10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Zboruri</title>
    <link rel="stylesheet" type="text/css" href="css/formstyle.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script>

        function createTable(resultParsat) {
            var content = "<tr><th>Oras plecare</th><th>Oras sosire</th> <th>Ora plecare</th><th>Ora sosire</th></tr>";
            $("#myTable").append(content);
            for (var i = 0; i < resultParsat.length; i++) {
                var element =
                    resultParsat[i];
                content = "<tr id = " + element.id + "><td>" + element.orasPlecare +
                    "</td><td>" + element.orasSosire +
                    "</td><td>" + element.oraPlecare +
                    "</td><td>" + element.oraSosire +
                    "</td><tr>";
                $("#myTable").append(content);
            }
        }

        function completeazaDatele(resultParsat) {
            var dateleExculuse = [0, 1, 2, 3, 4, 5, 6];
            console.log(dateleExculuse);
            for (var i = 0; i < resultParsat.length; i++) {
                var elem = resultParsat[i];
                dateleExculuse.splice(elem.zi - 1, 1);
            }
            console.log(dateleExculuse);
            return dateleExculuse;
        }

        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: '/getZboruri',
                success: function (result) {
                    var resultParsat = JSON.parse(result);
                    createTable(resultParsat);
                }
            });
            $(document).on("click", "table tr", function (e) {
                var idZbor = this.id;
                $.ajax({
                    type: 'GET',
                    data: {idZbor: idZbor},
                    url: '/putZborOnSession',
                    success: function (result) {
                        $.ajax({
                            type: 'GET',
                            data: {idZbor: idZbor},
                            url: '/getZile',
                            success: function (result) {
                                var resultParsat = JSON.parse(result);
                                var dateleExcluse = completeazaDatele(resultParsat);
                                $('#myDatePicker').datepicker({
                                    beforeShowDay: function (date) {
                                        var day = date.getDay();
                                        return [(faraDatele(dateleExcluse, day))];
                                    },
                                    minDate: 0,
                                    dateFormat: 'yy-mm-dd',
                                    inline: true,
                                    onSelect: function (d) {
                                        var date = $(this).datepicker('getDate');
                                        $.ajax({
                                                type: 'GET',
                                                data: {dataZbor: date.getTime()},
                                                url: '/putDataOnSession'
                                            }
                                        );
                                    }
                                });
                            }
                        });
                    }
                });
            });

            function faraDatele(dateleExcluse, day) {
                var rez = 1;
                for (var i = 0; i < dateleExcluse.length; i++) {
                    rez = rez && day !== dateleExcluse[i];
                }
                console.log(rez);
                return rez;
            }
        })
    </script>
</head>
<body>
<div class="container">
    <h1> Formular Rezervare </h1><br>
    <form method="post" action="/addRezervareMemory">
        <label>
            Nume:<br>
            <input type="text" name="nume" required/>
        </label>
        <br>
        <label>
            Prenume:<br>
            <input type="text" name="prenume" required/>
        </label>
        <br>
        <label>
            Email:<br>
            <input type="email" name="email" required/>
        </label>
        <br>
        <label>
            Zboruri: <br>
            <table id="myTable" class="table table-striped"></table>
        </label>
        <br>
        <label>
            Data: <br>
            <input id="myDatePicker" type="date" name="dataZbor">
        </label>
        <br>
        <button type="submit">Save</button>
    </form>
</div>

</body>
</html>

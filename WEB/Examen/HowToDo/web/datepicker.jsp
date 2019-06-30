<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 28.06.2019
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <style>
        input[type="date"]::-webkit-inner-spin-button,
        input[type="date"]::-webkit-calendar-picker-indicator {
            display: none;
            -webkit-appearance: none;
        }
    </style>
    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

    <script>
        $(document).ready(function () {
            $('#myDatePicker').datepicker({
                beforeShowDay: function(date) {
                    var day = date.getDay();
                    return [(day !== 0 && day !== 2)];
                },
                minDate: 0,
                dateFormat: 'yy-mm-dd',
                inline: true,
                onSelect: function(d) {
                    console.log(d);
                    var date = $(this).datepicker('getDate');
                    alert(date.getTime());
                }
            });
        })
    </script>
</head>
<body>
<label>
    Data: <br>
    <input id="myDatePicker" type="date" name="dataZbor">
</label>
</body>
</html>

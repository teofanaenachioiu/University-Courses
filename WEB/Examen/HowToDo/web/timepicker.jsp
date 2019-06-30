<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 28.06.2019
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Timepicker</title>
    <script src="js/jquery.js"></script>

    <%--<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">--%>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>
    <script>
        $('#timepicker').timepicker({
            timeFormat: 'hh:mm p',
            interval: 60,
            minTime: '10',
            maxTime: '6:00pm',
            defaultTime: '11',
            startTime: '10:00',
            dynamic: false,
            dropdown: true,
            scrollbar: true
        });

        $(document).ready(function(){
            $('input#timepicker').timepicker({}).click(function () {
                console.log($(this).val())
                }
            );
        });
    </script>
</head>
<body>
<label for="timepicker">Ora:</label><input readonly name="oraEv" type="text" id="timepicker">
</body>
</html>

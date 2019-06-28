<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 24.06.2019
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test</title>
    <script src="js/jquery.js"></script>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" type="text/css" href="css/listastyle.css">
    <link rel="stylesheet" type="text/css" href="css/myStyle.css">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        var catePagini;
        var done = false;
        var secundePerIntrebare = 7000;
        var valoare1 = 0;
        var valoare2 = 0;
        var valoare3 = 0;
        var nume = "teofff";
        var email = "t@ya.com";

        function adaugaContinut(nrPag, result) {
            var continut = '<form id="myForm"> ' +
                '<input name = "nrPag" type="text" hidden value = ' + nrPag +
                '>';
            if(nrPag ===1){
                continut += '<input id="numeUs" type="text" placeholder="Nume">' +
                    '<input id="emailUs" type="email" placeholder="Email"><br>';
            }
            for (var i = 0; i < result.length; i++) {
                var intrebare = result[i];
                continut +=
                    '<p>' + intrebare.id + '. ' + intrebare.intr + '</p>' +
                    '<input required type="radio" name="' + intrebare.id + '" value="1">' + intrebare.r1 + '<br>' +
                    '<input required type="radio" name="' + intrebare.id + '" value="2">' + intrebare.r2 + '<br>' +
                    '<input required type="radio" name="' + intrebare.id + '" value="3">' + intrebare.r3 + '<br>';
            }
            continut += "</form>";
            return continut;
        }

        function getIntrebari(nrPag) {
            $.ajax({
                type: 'GET',
                data: {nrPag: nrPag},
                url: '/getIntrebari',
                success: function (result) {
                    $("#cont").html(adaugaContinut(nrPag, JSON.parse(result)));
                    cronometru(nrPag,secundePerIntrebare);
                }
            });
        }

        function raspundeIntrebari(nrPag) {
            $.ajax({
                type: 'POST',
                data: {nrPag: nrPag, nume: nume, email:email, r1: valoare1, r2: valoare2, r3: valoare3},
                url: '/raspunde',
                success: function (result) {
                }
            });
        }

        function cronometru(nrPag, timpMax) {

            var radioName = (nrPag - 1) * 3 + 1;

            var radioName1 = radioName;
            var radioName2 = radioName+1;
            var radioName3 = radioName+2;

            $('input:radio[name="'+ radioName1+'"]').change(function () {
                valoare1 = $('input:radio[name="'+ radioName1+'"]:checked').val();
                console.log(valoare1);
            });
            $('input:radio[name="'+ radioName2+'"]').change(function () {
                valoare2 = $('input:radio[name="'+ radioName2+'"]:checked').val();
                console.log(valoare2);
            });
            $('input:radio[name="'+ radioName3+'"]').change(function () {
                valoare3 = $('input:radio[name="'+ radioName3+'"]:checked').val();
                console.log(valoare3);
            });


            var h2 = document.getElementsByTagName('h2')[0],
                seconds = 0, minutes = 0, hours = 0,
                t;
            h2.textContent = "00:00:00";

            function add() {
                seconds++;
                if (seconds >= 60) {
                    seconds = 0;
                    minutes++;
                    if (minutes >= 60) {
                        minutes = 0;
                        hours++;
                    }
                }
                h2.textContent = (hours ? (hours > 9 ? hours : "0" + hours) : "00") + ":" + (minutes ? (minutes > 9 ? minutes : "0" + minutes) : "00") + ":" + (seconds > 9 ? seconds : "0" + seconds);
                timer();
            }

            var timp = 0;

            function timer() {
                t = setTimeout(function () {
                        add();
                        timp += 1000;
                    },
                    1000
                );
                if (timp === timpMax) {
                    // nume =$('#numeUs').val();
                    // email =$('#emailUs').val();
                    console.log(nume);
                    console.log(email);
                    raspundeIntrebari(nrPag);
                    clearTimeout(t);
                    clearTime(nrPag);
                }
            }
            timer();
        }

        function clearTime(nrPag) {
            var h2 = document.getElementsByTagName('h2')[0];
            h2.textContent = "00:00:00";
            if (nrPag==catePagini) {
                $("h2").html("");
                $("#cont").html("done");
            }
        }

        function getNumarPagini() {
            $.ajax({
                type: 'GET',
                url: '/nrPagini',
                success: function (result) {
                    catePagini = result;

                    var nrPag = 1;
                    getIntrebari(nrPag);
                    nrPag = nrPag + 1;
                    var interv = setInterval(function () {
                        getIntrebari(nrPag);
                        nrPag = nrPag + 1;
                        if (nrPag > catePagini) {
                            clearInterval(interv);
                        }
                    }, secundePerIntrebare + 1000);
                }
            });
        }

        $(document).ready(function () {
            done=false;
            getNumarPagini();
        })

    </script>
</head>
<body>
<h1>Test</h1>
<h2>
    <time>00:00:00</time>
</h2>

<div id="cont" class="continut">

</div>
<h4>
    <%--<button disabled>1</button>--%>
    <%--<button disabled>2</button>--%>
    <%--<button disabled>3</button>--%>
</h4>
</body>
</html>

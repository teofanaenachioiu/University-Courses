<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 28.06.2019
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Examen</title>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/tabel.css">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        function cronometru() {
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
                console.log(timp);
                // if (timp === timpMax) {
                //     clearTimeout(t);
                //     clearTime();
                // }
            }
            timer();
        }

        // function clearTime() {
        //     var h2 = document.getElementsByTagName('h2')[0];
        //     h2.textContent = "00:00:00";
        //     console.log("clear");
        // }


        var descriere;
        var idImagine;
        var idDescriere;
        var path_img;

        function stergeElementele(idDescriere, idImagine) {
            $("#"+idImagine).find("#imagine").html("");
            $("#"+idDescriere).find("#descriere").html("");


        }

        function verificaPerechea(idDescriere, idImagine, path_img, descriere) {
            $.ajax({
                type: 'GET',
                data: {id: idImagine, descriere: descriere},
                url: '/verificaPerechea',
                success: function (result) {
                    console.log(result);
                    if (result == 1) {
                        console.log("e bun");
                        setTimeout(function () {
                            construireTabelBun(descriere, path_img);
                            stergeElementele(idDescriere, idImagine);
                        }, 3000);
                    }
                    if(result ==0)
                    {
                        console.log("nu e bun");
                        console.log($("#"+idImagine).html());
                        $("#"+idImagine).find("#imagine").addClass("rau");
                        $("#"+idDescriere).find("#descriere").addClass("rau");
                        setTimeout(function () {
                            $("#"+idImagine).find("#imagine").removeClass("rau");
                            $("#"+idDescriere).find("#descriere").removeClass("rau");
                        }, 3000);
                    }
                    if(result==2){
                        setTimeout(function () {
                            construireTabelBun(descriere, path_img);
                            stergeElementele(idDescriere, idImagine);
                        }, 3000);
                        setTimeout(function () {
                            window.location.href="/adaugaPoza.jsp";
                        },5000);

                    }
                    $("#"+idImagine).find("#imagine").removeClass("activ");
                    $("#"+idDescriere).find("#descriere").removeClass("activ");

                }
            });

        }

        function construireTabelBun(descriere, path_img) {
            var continut = '';
            continut += '<tr id="' + imagine.id +
                '"><td id="imagine"><img style="width: 200px" src = "' + path_img + '"' +
                '</td><td  id="descriere">' + descriere +
                '</td></tr>';

            $("#myTableBun").append(continut);
            evenimente();
        }

        function evenimente() {
            $("td").click(function () {
                $(this).addClass("activ");
                if ($(this).attr('id') === "descriere") {
                    idDescriere = $(this).parent().attr("id");
                    descriere = $(this).html();
                    // alert(descriere);
                }
                if ($(this).attr('id') === "imagine") {
                    idImagine = $(this).parent().attr("id");
                    path_img = $(this).children().attr("src");
                    // alert(path_img);
                }
                if (idImagine !== undefined && idDescriere !== undefined) {
                    verificaPerechea(idDescriere,idImagine, path_img, descriere);
                    $("#"+idDescriere).find("#descriere").removeClass("activ");
                    $("#"+idImagine).find("#imagine").removeClass("activ");
                    idImagine=undefined;
                    idDescriere=undefined;
                    descriere = undefined;
                    path_img = undefined;
                    // alert("se verifica");
                }
            })
        }

        function construireTabel(parse) {
            var continut = '<tr><th >Imagine</th><th>Descriere</th></tr>';
            for (var i = 0; i < parse.length; i++) {
                var imagine = parse[i];
                console.log(imagine.path_img);
                continut += '<tr id="' + imagine.id +
                    '"><td id="imagine"><img style="width: 200px" src = "' + imagine.path_img + '"' +
                    '</td><td  id="descriere">' + imagine.descriere +
                    '</td></tr>';
            }
            $("#myTable").html(continut);
            evenimente();
        }

        function getImagini() {
            $.ajax({
                type: 'GET',
                url: '/getImagini',
                success: function (result) {
                    construireTabel(JSON.parse(result));
                }
            });

        }

        $(document).ready(function () {
            getImagini();
            cronometru();
        })
    </script>
</head>

<body>

<table id="myTable">

</table>
<h2>
    <time>00:00:00</time>
</h2>
<table id="myTableBun">
    <tr>
        <th>Imagine</th>
        <th>Descriere</th>
    </tr>
</table>
</body>
</html>

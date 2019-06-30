<%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 27.06.2019
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fotografii</title>
    <link rel="stylesheet" type="text/css" href="css/meniu.css">
    <link rel="stylesheet" type="text/css" href="css/layoutStyle.css">
    <link rel="stylesheet" type="text/css" href="css/modal.css">
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script>
        var slideuri;
        var idSlideSelected;

        function selectOne(element) {

            $(".layout1").removeClass("activ");
            $(".layout2").removeClass("activ");
            $(element).addClass("activ");
            $("#left").removeAttr("disabled");
            $("#right").removeAttr("disabled");
            var idSlide = $(element).attr('id');
            idSlideSelected = idSlide;
            $("#idSlide").val(idSlide);
        }

        function intershimbaPozitii(id1, id2) {
            $.ajax({
                type: 'POST',
                data: {id1: id1, id2: id2},
                url: '/intershimbaPozitii'
            });
        }

        function construieste(slide) {
            var continut = '';
            if (slide.layout === "layout1") {
                continut += '<div class="layout1" id = "' + slide.id + '">';
            }
            else {
                continut += '<div class="layout2" id = "' + slide.id + '">';
            }

            continut += '<img src="' + slide.path_img + '">' +
                '    <p>' + slide.titlu + '</p>' +
                '</div>';

            return continut;
        }

        function doSomething(slide, timp) {
            setTimeout(function () {
                $("#slidesPlay").html(construieste(slide));
                console.log("dorm");
            }, timp);
        }

        function slideshow() {
            $("#myModal").show();
            var slidePrev = slideuri[0];
            $("#slidesPlay").html(construieste(slidePrev));
            var timp = slidePrev.nrSecunde *1000;
            console.log(timp);
            for(var i=1;i<slideuri.length;i++){
                var slideCurent = slideuri[i];
                doSomething(slideCurent,timp);
                timp += slideCurent.nrSecunde*1000;
            }
        }

        function openDiv(param) {
            var idSlide = $(param).attr('id');
            $.ajax({
                type: 'GET',
                data: {idSlide: idSlide},
                url: '/setSlide',
                success: function (result) {
                    window.open("/view.jsp", '_blank');
                }
            });

        }

        function evenimente() {
            $(".layout1").click(function () {
                selectOne(this);
                openDiv(this);
            }).dblclick(function () {
                $(".container").toggle();
            });
            $(".layout2").click(function () {
                selectOne(this);
                openDiv(this);
            }).dblclick(function () {
               $(".container").toggle();
            });

            $("#left").click(function () {
                var elemSelectat = $("#" + idSlideSelected);
                var elemPrev = elemSelectat.prev();
                var idPrev = $(elemPrev).attr("id");
                if (idPrev !== undefined) {
                    var continut = elemPrev.html();
                    var continut2 = elemSelectat.html();
                    elemSelectat.html(continut);
                    elemPrev.html(continut2);
                    elemSelectat.html(continut).removeClass("activ");
                    elemPrev.html(continut2).addClass("activ");
                    intershimbaPozitii(idSlideSelected, idPrev);
                    idSlideSelected = idPrev;
                }
            });
            $("#right").click(function () {
                var elemSelectat = $("#" + idSlideSelected);
                var elemNext = elemSelectat.next();
                var idNext = $(elemNext).attr("id");
                if (idNext !== undefined) {
                    var continut = elemNext.html();
                    var continut2 = elemSelectat.html();
                    elemSelectat.html(continut).removeClass("activ");
                    elemNext.html(continut2).addClass("activ");
                    intershimbaPozitii(idSlideSelected, idNext);
                    idSlideSelected = idNext;

                }
            });
            $("#play").click(function () {
                $.ajax({
                    type: 'GET',
                    url: '/getSlideuri',
                    success: function (result) {
                        slideuri = JSON.parse(result);
                        slideshow();
                    }
                });

            })
        }

        function creareThumb() {
            var continut = '';
            for (var i = 0; i < slideuri.length; i++) {
                var slide = slideuri[i];
                if (slide.layout === "layout1") {
                    continut += '<div class="layout1" id = "' + slide.id + '">';
                }
                else {
                    continut += '<div class="layout2" id = "' + slide.id + '">';
                }

                continut += '<img src="' + slide.path_img + '">' +
                    '    <p>' + slide.titlu + '</p>' +
                    '</div>';
            }
            return continut;
        }

        function getSlideuri() {
            $.ajax({
                type: 'GET',
                url: '/getSlideuri',
                success: function (result) {
                    slideuri = JSON.parse(result);
                    if (slideuri.length > 1) {
                        $("#play").removeAttr("disabled");
                    }
                    $("#mySlides").html(creareThumb());
                    evenimente();
                }
            });
        }


        $(document).ready(function () {
            getSlideuri();
            Window.onclick = function (event) {
                var modal = $("#myModal");
                if (event.target === modal) {
                    modal.hide();
                }
            };
            $("#inchide").click(function () {
                $("#myModal").hide();
            });
        })

    </script>
</head>
<body>
<ul class="meniu">
    <li><a href="/index.jsp">View</a></li>
    <li><a href="/creare.jsp">Creare</a></li>
</ul>
<div style="position: fixed; z-index: 1; width: 100px; background-color: black">
    <button disabled id="left"><i class="fa fa-arrow-left"></i></button>
    <button disabled id="right"><i class="fa fa-arrow-right"></i></button>
    <button disabled id="play"><i class="fa fa-play"></i></button>
</div>
<br>
<div id="mySlides">
</div>

<!-- The Modal -->
<div id="myModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span id="inchide" class="close">&times;</span>
        <div id="slidesPlay"></div>
    </div>
</div>


<div class="container" style="display: none">
    <h1>Editare slide</h1>
    <hr>

    <form id="myForm" method="post" action="/editareSlide" enctype="multipart/form-data">
        <input type="text" name="titlu" placeholder="Titlu"><br>
        <input type="text" name="idSlide" hidden id="idSlide">
        <textarea name="text" rows="5" placeholder="Text" form="myForm"></textarea><br>
        <input type="number" name="nrSecunde" placeholder="nrSec"><br>
        <label>
            Imagine:<br>
            <input type="file" name="imagine">
        </label><br>
        <label>
            Layout:<br>
            <input type="radio" name="layout" value="layout1"> Layout 1<br>
            <input type="radio" name="layout" value="layout2"> Layout 2<br>
        </label><br>
        <button id="save" type="submit">Save</button>
    </form>
    <hr>
</div>
</body>
</html>

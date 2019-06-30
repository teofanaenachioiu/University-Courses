<%@ page import="webapp.model.Slide" %><%--
  Created by IntelliJ IDEA.
  User: Teofana
  Date: 28.06.2019
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Slide</title>
    <link rel="stylesheet" type="text/css" href="css/layoutStyle.css">
    <script src="js/jquery.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
    <script>
        function construieste(imagine, text, layout) {
            var continut = '';
            if (layout === "layout1") {
                continut += '<div class="layout1">';
            }
            else {
                continut += '<div class="layout2">';
            }

            continut += '<img src="' + imagine + '">' +
                '    <p>' + text + '</p>' +
                '</div>';

            return continut;
        }
        $(document).ready(function () {
            <%
            Slide slide = (Slide) session.getAttribute("slide");%>
            var imagine = "<%out.print(slide.getPath_img());%>";
            var text = "<%out.print(slide.getText());%>";
            var layout = "<%out.print(slide.getLayout());%>";
            <%--var slide = JSON.parse("<%out.print(slide);%>");--%>
            $("#mySlides").html(construieste(imagine,text, layout));
            // console.log(slide);
        })
    </script>
</head>
<body>
<div id="mySlides">
</div>
</body>
</html>

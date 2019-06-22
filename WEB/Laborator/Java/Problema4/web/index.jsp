<%--
  Created by IntelliJ IDEA.
  User: teofana
  Date: 5/30/2019
  Time: 12:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "ct" uri = "pufuleti"%>
<html>
<head>
  <title>Problema 4</title>

  <style>
    * {box-sizing: border-box;}
    ul {list-style-type: none;}
    body {font-family: Verdana, sans-serif;}

    .month {
      padding: 5px 25px;
      width: 70%;
      text-align: center;
    }

    .month ul {
      margin: 0;
      padding: 0;
    }

    .month ul li {
      color: white;
      font-size: 20px;
      text-transform: uppercase;
      letter-spacing: 3px;
    }

    .month .prev {
      float: left;
      padding-top: 10px;
    }

    .month .next {
      float: right;
      padding-top: 10px;
    }

    .weekdays {
      width: 70%;
      padding: 7px 0;
      background-color: black;
    }

    .weekdays li {
      display: inline-block;
      width: 13%;
      color: darkseagreen;
      text-align: center;
    }

    .days {
      width: 70%;
      padding: 8px 0;
      background: white;
      margin: 0;
    }

    .days li {
      list-style-type: none;
      display: inline-block;
      width: 13%;
      text-align: center;
      margin-bottom: 5px;
      font-size:12px;
      color: indianred;
    }

    .days li .active {
      padding: 5px;

      color: white !important
    }
  </style>
</head>
<body>

<ct:calendar an="2019" luna="10" zi="4" culoare="red" style="opacity: 0.5;"/>

</body>
</html>
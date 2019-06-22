<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Problema 6</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="script.js"></script>
  <style>
    h1 {
      text-align: center;
    }
    td {
      width: 100px;
      height: 100px;
    }
    table {
      margin: 5px auto;
      border-width: 1px;
    }
  </style>
</head>
<body>
  <table id="table" border="1" >

  </table>
  <button id="new">New Game</button>
  <%--<button id="refresh">Refresh</button>--%>
  <div id="wait">Wait for an oponent</div>
  <div id="win"></div>
</body>
</html>
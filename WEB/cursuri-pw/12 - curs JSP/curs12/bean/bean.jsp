<%@page errorPage="error.jsp"%> 
<jsp:useBean id="record" class="curs13.Record" scope="session"/>
<jsp:setProperty name="record" property="*"/>
<html>
<head><title>Multumim pentru inregistrare</title></head>
<body>
    Am salvat datele din formular.
    Va rugam sa <a href="next.jsp">continuati</a>.
</body>
</html>

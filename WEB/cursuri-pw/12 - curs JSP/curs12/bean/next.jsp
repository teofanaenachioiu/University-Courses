<jsp:useBean id="record" class="curs13.Record" scope="session"/>
<html>
<body>
Ati introdus in formularul de inregistrare urmatoarele date:<br/>
Nume: <jsp:getProperty name="record" property="nume"/><br/> 
Prenume: <jsp:getProperty name="record" property="prenume"/><br/>
E-mail: <jsp:getProperty name="record" property="email"/><br/>
Telefon: <jsp:getProperty name="record" property="telefon"/><br/>
Varsta: <jsp:getProperty name="record" property="varsta"/><br/>
Parola: <jsp:getProperty name="record" property="parola"/><br/>
Parola2: <%= record.getParola2() %><br/>
</body>
</html>

<%
  String login = (String) session.getAttribute("login");
  if (login == null) {
    response.sendRedirect("login.html");
    return;
  }
  if (! login.equals("true"))
    response.sendRedirect("login.html");
  
%>

Autentificare efectuata cu succes.<br>
In acest moment aplicatia de demo va permite doar sa va <a href="logout.jsp">delogati</a>.

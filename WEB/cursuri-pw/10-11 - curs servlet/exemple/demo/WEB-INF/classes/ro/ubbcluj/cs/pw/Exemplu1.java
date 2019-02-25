package ro.ubbcluj.cs.pw;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Exemplu1 extends HttpServlet {


  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
  
    response.setContentType("text/html");

    PrintWriter out = response.getWriter();
    out.println("<br>Antetele primite sunt: <br>");

    Enumeration e = request.getHeaderNames();
    while (e.hasMoreElements()) {
      String header = (String) e.nextElement();
      out.println(header + " = " + request.getHeader(header) + "<br>");
    }

    out.println("<br>Metoda prin care a fost invocat acest servlet est: " + request.getMethod() + "<br>");

    out.println("<br>Parametrii primiti prin " + request.getMethod() + " sunt:<br>");

    e = request.getParameterNames();
    while (e.hasMoreElements()) {
      String param = (String) e.nextElement();
      out.println(param + " = " + request.getParameter(param) + "<br>");
    }
  }
}

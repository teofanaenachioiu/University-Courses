package ro.ubbcluj.cs.pw;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String user = request.getParameter("user");
    String password = request.getParameter("password");

    try {
      ServletContext application = request.getSession().getServletContext();
      Connection con = (Connection) application.getAttribute("conexiune");
      Statement query = con.createStatement();
      ResultSet rs = query.executeQuery("SELECT parola FROM useri WHERE email='" + user + "'");
      rs.next();
      if (rs.getString("parola").equals(password)) {
        // password ok
        HttpSession session = request.getSession();
        session.setAttribute("login", "true");
        response.sendRedirect("welcome.jsp");
      }
      else {
        response.sendRedirect("login.html");
      }
    }
    catch (SQLException e) {
      System.err.println(e);
      response.sendRedirect("login.html");
    }
  }



}

package ro.ubbcluj.cs.pw;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet {

  java.lang.String dataBaseURL = null;
  java.lang.String dataBaseUser = null;
  java.lang.String dataBasePassword = null;

  java.sql.Connection con = null;

  public void init()
    throws ServletException {
    dataBaseURL = getInitParameter("DataBaseURL");
    dataBaseUser = getInitParameter("DataBaseUser");
    dataBasePassword = getInitParameter("DataBasePassword");

    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(dataBaseURL, dataBaseUser, dataBasePassword);
      System.err.println("Am deschis conexiunea la baza de date.");

    }
    catch (SQLException e) {
      System.err.println("Eroare la conectarea la baza de date");
    }
    catch (ClassNotFoundException e) {
      System.err.println("Driverul MySQL nu poate fi localizat");
    }
  }

  public void destroy() {
    try {
      con.close();
      System.err.println("Am inchis conexiunea la baza de date.");
    }
    catch (SQLException e) {
      System.err.println("Eroare la deconectarea de la baza de date");
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String user = request.getParameter("user");
    String password = request.getParameter("password");

    try {
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

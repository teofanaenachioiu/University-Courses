package ro.ubbcluj.cs.pw;

import java.sql.*;
import javax.servlet.*;

public final class ApplicationManagement implements ServletContextListener {

  public void contextInitialized(ServletContextEvent event) {
    ServletContext application = event.getServletContext();

    String dataBaseURL = application.getInitParameter("DataBaseURL");
    String dataBaseUser = application.getInitParameter("DataBaseUser");
    String dataBasePassword = application.getInitParameter("DataBasePassword");

    try {
      Class.forName("com.mysql.jdbc.Driver");
      Connection con = DriverManager.getConnection(dataBaseURL, dataBaseUser, dataBasePassword);
      application.setAttribute("conexiune", con);
      System.err.println("Am deschis conexiunea la baza de date.");
    }
    catch (SQLException e) {
      System.err.println("Eroare la conectarea la baza de date");
    }
    catch (ClassNotFoundException e) {
      System.err.println("Driverul MySQL nu poate fi localizat");
    }

  }

  public void contextDestroyed(ServletContextEvent event) {
    ServletContext application = event.getServletContext();
    Connection con = (Connection) application.getAttribute("conexiune");
    try {
      con.close();
      System.err.println("Am inchis conexiunea la baza de date.");
    }
    catch (SQLException e) {
      System.err.println("Eroare la deconectarea de la baza de date");
    }
  }

}

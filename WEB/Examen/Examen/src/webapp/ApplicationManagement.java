package webapp;

import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationManagement implements ServletContextListener {
    private static Connection connection;
    private static ServletContext application;

    public void contextInitialized(ServletContextEvent event) {
        application = event.getServletContext();

        String dataBaseURL = application.getInitParameter("DataBaseURL");
        String dataBaseUser = application.getInitParameter("DataBaseUser");
        String dataBasePassword = application.getInitParameter("DataBasePassword");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dataBaseURL, dataBaseUser, dataBasePassword);
            application.setAttribute("conexiune", connection);
            System.err.println("Am deschis conexiunea la baza de date.");
        } catch (SQLException e) {
            System.err.println("Eroare la conectarea la baza de date");
        } catch (ClassNotFoundException e) {
            System.err.println("Driverul MySQL nu poate fi localizat");
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        ServletContext application = event.getServletContext();
        Connection con = (Connection) application.getAttribute("conexiune");
        try {
            con.close();
            System.err.println("Am inchis conexiunea la baza de date.");
        } catch (SQLException e) {
            System.err.println("Eroare la deconectarea de la baza de date");
        }
    }

    public static Utilizator autentificare(HttpSession session, String username, String password) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT password FROM utilizatori WHERE username=?");

            preparedStatement.setString(1, username);
            System.out.println(username);
            System.out.println(password);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next() && rs.getString("password").equals(password)) {
                Utilizator utilizator = Utilizator.builder().password(password).username(username).build();
                session.setAttribute("utilizator", utilizator);
                return utilizator;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static int addUser(String username, String password) {
//        PreparedStatement preparedStatement;
//        int key = 0;
//        try {
//            preparedStatement = connection.prepareStatement(
//                    "INSERT INTO utilizatori(username, password, path_avatar) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//            preparedStatement.setString(3, "/////");
//            preparedStatement.executeUpdate();
//            ResultSet rs = preparedStatement.getGeneratedKeys();
//
//            if (rs.next()) {
//                key = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return key;
//    }
//
//    public static List<Utilizator> getUtilizatori() {
//        List<Utilizator> list = new ArrayList<>();
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = connection.prepareStatement(
//                    "SELECT username, path_avatar, datan, descriere FROM utilizatori");
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                String username = rs.getString("username");
//                String path_avatar = rs.getString("path_avatar");
//                Long datan = rs.getLong("datan");
//                String descriere = rs.getString("descriere");
//                Utilizator utilizator = Utilizator.builder()
//                        .username(username)
//                        .build();
//                list.add(utilizator);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

}

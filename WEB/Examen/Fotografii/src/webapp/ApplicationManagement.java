package webapp;

import webapp.model.Incarcare;
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

    public static List<Incarcare> getIncarcari(String username) {
        List<Incarcare> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT id, path_poza, descriere FROM incarcari WHERE username = ?");
            preparedStatement.setString(1,username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String path_poza = rs.getString("path_poza");
                String descriere = rs.getString("descriere");
                Integer id = rs.getInt("id");
                Incarcare incarcare = Incarcare.builder()
                        .id(id)
                        .descriere(descriere)
                        .path_poza(path_poza)
                        .username(username)
                        .build();
                list.add(incarcare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<Incarcare> getIncarcariByKey(String keyword) {
        List<Incarcare> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT id, path_poza, username, descriere FROM incarcari WHERE descriere LIKE CONCAT('%', ?,'%')");
            preparedStatement.setString(1,keyword);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String path_poza = rs.getString("path_poza");
                String descriere = rs.getString("descriere");
                String username = rs.getString("username");
                Integer id = rs.getInt("id");
                Incarcare incarcare = Incarcare.builder()
                        .id(id)
                        .descriere(descriere)
                        .path_poza(path_poza)
                        .username(username)
                        .build();
                list.add(incarcare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Incarcare> getAllIncarcari() {
        List<Incarcare> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT id, username, path_poza, descriere FROM incarcari");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String path_poza = rs.getString("path_poza");
                String username = rs.getString("username");
                String descriere = rs.getString("descriere");
                Integer id = rs.getInt("id");
                Incarcare incarcare = Incarcare.builder()
                        .id(id)
                        .descriere(descriere)
                        .path_poza(path_poza)
                        .username(username)
                        .build();
                list.add(incarcare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static void stergePoza(Integer id) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM incarcari WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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

    public static List<Utilizator> getUtilizatori() {
        List<Utilizator> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT username, password FROM utilizatori");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                Utilizator utilizator = Utilizator.builder()
                        .username(username)
                        .password(password)
                        .build();
                list.add(utilizator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

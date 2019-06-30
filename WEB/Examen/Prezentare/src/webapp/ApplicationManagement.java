package webapp;

import webapp.model.Slide;
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

    public static void stergeSlide(Integer idSlide) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM slideuri WHERE id = ?");
            preparedStatement.setInt(1,idSlide);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int numarSlideuri() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(*) AS nrSlideuri FROM slideuri");
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getInt("nrSlideuri");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Slide getSlide(Integer idSlide) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT titlu, text, path_img, nrSecunde, layout, nrOrd FROM slideuri WHERE id=?");

            preparedStatement.setInt(1, idSlide);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return Slide.builder()
                        .id(idSlide)
                        .titlu(rs.getString("titlu"))
                        .text(rs.getString("text"))
                        .layout(rs.getString("layout"))
                        .nrSecunde(rs.getInt("nrSecunde"))
                        .path_img(rs.getString("path_img"))
                        .nrOrd(rs.getInt("nrOrd"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Slide.builder().build();
    }

    public static List<Slide> getSlideuri() {
        List<Slide> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT  id, titlu, text, path_img, nrSecunde, layout, nrOrd FROM slideuri ORDER BY nrOrd");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Slide slide= Slide.builder()
                        .id(rs.getInt("id"))
                        .titlu(rs.getString("titlu"))
                        .text(rs.getString("text"))
                        .layout(rs.getString("layout"))
                        .nrSecunde(rs.getInt("nrSecunde"))
                        .nrOrd(rs.getInt("nrOrd"))
                        .path_img(rs.getString("path_img"))
                        .build();
                list.add(slide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void interschimbaPozitii(Integer id1, Integer id2) {
        Slide slide1= getSlide(id1);
        Slide slide2= getSlide(id2);

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE slideuri SET nrOrd=? WHERE id = ?");
            preparedStatement.setInt(1, slide2.getNrOrd());
            preparedStatement.setInt(2, slide1.getId());
            preparedStatement.execute();


            preparedStatement = connection.prepareStatement(
                    "UPDATE slideuri SET nrOrd=? WHERE id = ?");
            preparedStatement.setInt(1, slide1.getNrOrd());
            preparedStatement.setInt(2, slide2.getId());
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

    public static int addUser(String username, String password) {
        PreparedStatement preparedStatement;
        int key = 0;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO utilizatori(username, password, path_avatar) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "/////");
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                key = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public static List<Utilizator> getUtilizatori() {
        List<Utilizator> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT username, path_avatar, datan, descriere FROM utilizatori");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String path_avatar = rs.getString("path_avatar");
                Long datan = rs.getLong("datan");
                String descriere = rs.getString("descriere");
                Utilizator utilizator = Utilizator.builder()
                        .username(username)
                        .build();
                list.add(utilizator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}

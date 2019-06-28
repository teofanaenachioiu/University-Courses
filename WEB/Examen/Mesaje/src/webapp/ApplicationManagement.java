package webapp;

import webapp.model.Mesaj;
import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
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

    public static Utilizator autentificare(HttpServletRequest req, String username, String password) {

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT password FROM utilizatori WHERE username=?");

            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next() && rs.getString("password").equals(password)) {
                Utilizator utilizator = Utilizator.builder().password(password).username(username).build();
                req.getSession().setAttribute("utilizator", utilizator);
                return utilizator;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Mesaj> getMesajePrimite(String username, int pagina) {
        List<Mesaj> list = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT id, userExp, subiect,  dataExp, citit  FROM mesaje WHERE userDest=? LIMIT ?, 3");
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, pagina);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                Mesaj mesaj = Mesaj.builder()
                        .id(rs.getInt("id"))
                        .userExp(rs.getString("userExp"))
                        .userDest(username)
                        .subiect(rs.getString("subiect"))
                        .dataExp(rs.getLong("dataExp"))
                        .citit(rs.getBoolean("citit"))
                        .build();
                list.add(mesaj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static void marcheazaCitit(int idMesaj) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE mesaje SET citit=true WHERE id= ?");
            preparedStatement.setInt(1, idMesaj);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getConitnutMesaj(HttpServletRequest req, int idMesaj) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT continut, userExp FROM mesaje WHERE id= ?");
            preparedStatement.setInt(1, idMesaj);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String continut = rs.getString("continut");
                HttpSession session = req.getSession();
                session.setAttribute("userDest", rs.getString("userExp"));
                marcheazaCitit(idMesaj);
                return continut;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteMesaj(int idMesaj) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM mesaje WHERE id= ?");
            preparedStatement.setInt(1, idMesaj);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getDeastinatari(String username) {
        PreparedStatement preparedStatement = null;
        List<String> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT username FROM utilizatori WHERE username <> ?");
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String user = rs.getString("username");
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void adaugaMesaj(String userExp, String userDest, String subiect, String continut) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO mesaje(userExp, userDest, subiect, continut, dataExp, citit) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, userExp);
            preparedStatement.setString(2, userDest);
            preparedStatement.setString(3, subiect);
            preparedStatement.setString(4, continut);
            preparedStatement.setLong(5, System.currentTimeMillis());
            preparedStatement.setBoolean(6,false);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

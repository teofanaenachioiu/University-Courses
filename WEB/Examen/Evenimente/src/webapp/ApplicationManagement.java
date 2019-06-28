package webapp;

import webapp.model.Eveniment;
import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationManagement implements ServletContextListener {
    private static Connection connection;
    private static ServletContext application;

    public static void adaugaEveniment(Long dataEv, Long oraEv, String descriere) {
        PreparedStatement preparedStatement;
        int key = 0;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO evenimente(dataEv,oraEv, descriere) VALUES (?,?,?)");
            preparedStatement.setLong(1, dataEv);
            preparedStatement.setLong(2, oraEv);
            preparedStatement.setString(3, descriere);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Long> getDate() {
        Statement statement = null;
        List<Long> list = new ArrayList<>();
        try {
            statement = connection.createStatement();

            String query =
                    "SELECT distinct dataEv FROM evenimente order by dataEv";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Long dataEv = rs.getLong("dataEv");
                list.add(dataEv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Eveniment> getContinut(Long dataEv) {
        PreparedStatement statement = null;
        List<Eveniment> list = new ArrayList<>();
        try {
            statement = connection.prepareStatement("SELECT id, oraEv, descriere FROM evenimente WHERE dataEv =?");
            statement.setLong(1, dataEv);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long oraEv = rs.getLong("oraEv");
                String descriere = rs.getString("descriere");
                Integer id = rs.getInt("id");
                list.add(Eveniment.builder()
                        .id(id)
                        .dataEv(dataEv)
                        .oraEv(oraEv)
                        .descriere(descriere)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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

    public static Utilizator autentificare(String username, String password) {

        Statement statement = null;
        try {
            statement = connection.createStatement();

            String query =
                    "SELECT COUNT(*) as NrEl FROM utilizatori WHERE username='" + username + "' and password = '"+password+"'";
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                Integer nrElemente = rs.getInt("NrEl");
                System.out.println(nrElemente);
                if (nrElemente.equals(1)) {
                    Utilizator utilizator = Utilizator.builder().password(password).username(username).build();
                    application.setAttribute("utilizator", utilizator);
                    return utilizator;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}

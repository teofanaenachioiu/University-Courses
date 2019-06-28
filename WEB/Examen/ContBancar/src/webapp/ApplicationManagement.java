package webapp;

import webapp.model.Tranzactie;
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



    public void contextInitialized(ServletContextEvent event) {
        ServletContext application = event.getServletContext();

        String dataBaseURL = application.getInitParameter("DataBaseURL");
        String dataBaseUser = application.getInitParameter("DataBaseUser");
        String dataBasePassword = application.getInitParameter("DataBasePassword");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dataBaseURL, dataBaseUser, dataBasePassword);
            application.setAttribute("conexiune", connection);
            application.setAttribute("penalizare", 0);
            application.setAttribute("nrLog", 0);
            application.setAttribute("lastLog", (long)0);
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

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT password, nume, cont, suma FROM utilizatori WHERE username=?");

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next() && rs.getString("password").equals(password)) {
                Utilizator utilizator = Utilizator.builder()
                        .password(password)
                        .username(username)
                        .suma(Integer.valueOf(rs.getString("suma")))
                        .cont(Integer.valueOf(rs.getString("cont")))
                        .nume(rs.getString("nume"))
                        .build();
                session.setAttribute("utilizator", utilizator);
                return utilizator;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Utilizator> getUtilizatori(String username) {
        PreparedStatement preparedStatement;
        List<Utilizator> lista = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT password, nume, cont, suma, username FROM utilizatori WHERE username<>?");

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Utilizator utilizator = Utilizator.builder()
                        .password(rs.getString("password"))
                        .username(rs.getString("username"))
                        .suma(Integer.valueOf(rs.getString("suma")))
                        .cont(Integer.valueOf(rs.getString("cont")))
                        .nume(rs.getString("nume"))
                        .build();
                lista.add(utilizator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private static int getSumaCont(Integer cont){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT suma FROM utilizatori WHERE cont=?");
            preparedStatement.setInt(1, cont);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getInt("suma");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Tranzactie> getTranzactii(Integer cont){
        PreparedStatement preparedStatement;
        List<Tranzactie> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT id, contExp, contDest, suma, dataora FROM tranzactii WHERE contExp=? or contDest=?");
            preparedStatement.setInt(1, cont);
            preparedStatement.setInt(2, cont);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Tranzactie tranzactie = Tranzactie.builder()
                        .id(rs.getInt("id"))
                        .contDest(rs.getInt("contDest"))
                        .contExp(rs.getInt("contExp"))
                        .suma(rs.getInt("suma"))
                        .dataora(rs.getLong("dataora"))
                        .build();
                list.add(tranzactie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void updateSumaCont(Integer cont, Integer suma){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE utilizatori SET suma =? WHERE cont=?");
            preparedStatement.setInt(1, suma);
            preparedStatement.setInt(2, cont);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void transferaBani(Integer contExp, Integer contDest, Integer suma) {
        PreparedStatement preparedStatement;
        Long dataora = System.currentTimeMillis();
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO tranzactii(contExp, contDest, suma, dataora) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, contExp);
            preparedStatement.setInt(2, contDest);
            preparedStatement.setInt(3, suma);
            preparedStatement.setLong(4, dataora);
            preparedStatement.execute();
            updateSumaCont(contDest, getSumaCont(contDest)+suma);
            updateSumaCont(contExp, getSumaCont(contExp)-suma);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

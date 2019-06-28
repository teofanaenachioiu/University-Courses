package webapp.controller;

import javafx.util.Pair;
import webapp.SendEmail;
import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/addRezervare")
public class AddRezervareController extends HttpServlet {

    List<Pair<String,String>> getPasageri(String pasageri){
        List<Pair<String,String>> rezultat = new ArrayList<>();
        String[] pasager = pasageri.split(";");
        for(String elem : pasager){
            String [] el = elem.split(",");
            if(el.length ==1) return new ArrayList<>();
            rezultat.add(new Pair<>(el[0],el[1]));
        }
        return rezultat;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pasageri = request.getParameter("pasageri");
        List<Pair<String, String>> rezultatPasageri = getPasageri(pasageri);
        HttpSession session = request.getSession();
        String nume = (String) session.getAttribute("nume");
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        String prenume = (String) session.getAttribute("prenume");
        String email = (String) session.getAttribute("email");
        Integer idZbor = (Integer) session.getAttribute("idZbor");
        Long dataZbor = (Long) session.getAttribute("dataZbor");
        rezultatPasageri.add(new Pair<>(nume,prenume));

        try {
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");

            String sql = "INSERT INTO rezervari(idZbor, dataZbor,username) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, idZbor);
            preparedStatement.setLong(2, dataZbor);
            preparedStatement.setString(3, utilizator.getUsername());
            Integer idRezervare = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            int risultato = 0;
            if (rs.next()) {
                risultato = rs.getInt(1);
            }
            for(Pair el: rezultatPasageri){
                sql = "INSERT INTO persoane (nume, prenume, email, idRezervare) VALUES (?,?,?,?) ";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, (String) el.getKey());
                preparedStatement.setString(2, (String) el.getValue());
                preparedStatement.setString(3, email);
                preparedStatement.setInt(4, risultato);
                preparedStatement.execute();
            }


            SendEmail emailDeTrimis=new SendEmail("teofanaenachioiu@yahoo.com",
                    "<h1>Rezervare noua</h1>",
                    "Rezervare"
            );
            emailDeTrimis.run();

            response.sendRedirect("formular.jsp");
        } catch (SQLException e1) {
            e1.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}

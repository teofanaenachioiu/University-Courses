package webapp.controller;

import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

@WebServlet("/raspundeIntrebare")
public class RaspundeController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String mesaj = request.getParameter("mesaj");
        int idIntrebare = Integer.parseInt(request.getParameter("idIntrebare"));
        long data_rasp = System.currentTimeMillis();
        System.out.println(mesaj);
        System.out.println(idIntrebare);
        System.out.println(data_rasp);
        HttpSession session = request.getSession();
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        System.out.println(utilizator);
        try {
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");

            String sql = "INSERT INTO raspunsuri(mesaj, data_rasp, idIntrebare, username) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mesaj);
            preparedStatement.setLong(2, data_rasp);
            preparedStatement.setInt(3, idIntrebare);
            preparedStatement.setString(4, utilizator.getUsername());
            preparedStatement.execute();
            response.sendRedirect("indexAuth.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("indexAuth.jsp");
        }
    }
}

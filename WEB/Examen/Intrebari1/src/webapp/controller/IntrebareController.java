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

@WebServlet("/intrebare")
public class IntrebareController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String mesaj = request.getParameter("mesaj");
        long data_intr = System.currentTimeMillis();
        System.out.println(mesaj);
        System.out.println(data_intr);
        HttpSession session = request.getSession();
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        System.out.println(utilizator);
        try {
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");

            String sql = "INSERT INTO intrebari(mesaj, data_intr, username) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mesaj);
            preparedStatement.setLong(2, data_intr);
            preparedStatement.setString(3, utilizator.getUsername());
            preparedStatement.execute();
            response.sendRedirect("indexAuth.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("indexAuth.jsp");
        }
    }
}


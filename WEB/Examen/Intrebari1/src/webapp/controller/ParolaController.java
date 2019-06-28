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

@WebServlet("/schimbaParola")
public class ParolaController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String parolaNoua = request.getParameter("password");

        HttpSession session = request.getSession();
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");

        try {
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");

            String sql = "UPDATE utilizatori SET password = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, parolaNoua);
            preparedStatement.setString(2, utilizator.getUsername());
            preparedStatement.execute();
            response.sendRedirect("indexAuth.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("settings.jsp");
        }
    }
}

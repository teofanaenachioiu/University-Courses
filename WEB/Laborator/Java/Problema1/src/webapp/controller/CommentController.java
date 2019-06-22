package webapp.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet("/comment")
public class CommentController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String message = request.getParameter("message");

        ServletContext application = request.getSession().getServletContext();
        Connection connection = (Connection) application.getAttribute("conexiune");
        String sql = "INSERT INTO messages(username, message, active) VALUES(?,?, 0)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, EscapeUtils.escapeHtml(username));
            preparedStatement.setString(2, EscapeUtils.escapeHtml(message));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        response.sendRedirect("index.jsp");
    }
}

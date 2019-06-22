package webapp.model;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/addComment")
public class AddCommentController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String comentariu = request.getParameter("comentariu");

        String sql = "INSERT INTO messages(message) VALUES(?)";
        try {
            ServletContext application = request.getSession().getServletContext();
            Connection con = (Connection) application.getAttribute("conexiune");
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, comentariu);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("index.jsp");
    }
}

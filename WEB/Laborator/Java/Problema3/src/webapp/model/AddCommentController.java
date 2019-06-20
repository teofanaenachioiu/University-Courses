package webapp.model;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/addRegex")
public class AddRegexController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String regex = request.getParameter("regex");

        System.out.println("REGEX: "+ regex);
        String sql = "INSERT INTO expresii(regex) VALUES(?)";
        try {
            ServletContext application = request.getSession().getServletContext();
            Connection con = (Connection) application.getAttribute("conexiune");
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, regex);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("indexAdmin.jsp");
    }
}

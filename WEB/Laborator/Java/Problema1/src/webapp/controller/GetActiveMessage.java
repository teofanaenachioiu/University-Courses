package webapp.controller;

import webapp.model.Mesaj;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet("/getAllInactive")
public class GetInactiveMessage extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Mesaj> list = new ArrayList<>();

        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT id, username, message FROM messages WHERE active=0");
            while(rs.next()){
                Integer id = rs.getInt("id");
                String userName = rs.getString("username");
                String msg = rs.getString("message");

                list.add(new Mesaj(id, userName,msg));
            }
        } catch (SQLException e) {
            System.err.println(e);
            response.sendRedirect("index.jsp");
        }

        PrintWriter outp = response.getWriter();

        for(Mesaj m: list) {
            outp.println( "<tr><td>" );
            outp.println("<form action ='AcceptMessageController'>");
            outp.println("<input type='hidden' name='id' value = '" + m.getId()+ "'>");
            outp.println( "<button type='submit'> Aproba </button>");
            outp.println("</form>");
            outp.println("</td><td>");
            outp.println("<strong>" +m.getUsername() +":" + "</strong>");
            outp.println("<p>"+m.getMsg()+ "</p>");
            outp.println("</td></tr>");
        }

    }
}

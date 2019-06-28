package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Zbor;

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

@WebServlet("/getZboruri")
public class GetZboruriController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Zbor> list = new ArrayList<>();

        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT  id,orasPlecare, orasSosire, oraPlecare, oraSosire FROM zboruri");
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String orasPlecare = rs.getString("orasPlecare");
                String orasSosire = rs.getString("orasSosire");
                String oraPlecare = rs.getString("oraPlecare");
                String oraSosire = rs.getString("oraSosire");
                list.add(Zbor.builder()
                        .id(id)
                        .oraPlecare(oraPlecare)
                        .oraSosire(oraSosire)
                        .orasPlecare(orasPlecare)
                        .orasSosire(orasSosire)
                        .build());
            }
        } catch (SQLException e) {
            System.err.println(e);
            response.sendRedirect("index.jsp");
        }

        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = response.getWriter();
        outp.println(jsonFromJavaArrayList);
    }
}

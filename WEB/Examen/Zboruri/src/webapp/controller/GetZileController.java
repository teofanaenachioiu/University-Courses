package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.DisponibilitateZbor;

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

@WebServlet("/getZile")
public class GetZileController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<DisponibilitateZbor> list = new ArrayList<>();
        Integer idZbor = Integer.valueOf(request.getParameter("idZbor"));
        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT  id, zi FROM disponibilitatezboruri WHERE idZbor = "+ idZbor);
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer zi = rs.getInt("zi");
                list.add(DisponibilitateZbor.builder()
                        .id(id)
                        .zi(zi)
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

package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Popularitate;
import webapp.model.Produs;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/popularitate")
public class PopularitateController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Popularitate> list = new ArrayList<>();
        Integer idProdus = Integer.valueOf(request.getParameter("idProdus"));
        System.out.println(idProdus);
        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            String sqlSelect = "SELECT nrLuna, nrViews FROM popularitate WHERE idProdus = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, idProdus);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer nrLuna = rs.getInt("nrLuna");
                Integer nrViews = rs.getInt("nrViews");
                list.add(Popularitate.builder()
                        .idProdus(idProdus)
                        .nrLuna(nrLuna)
                        .nrViews(nrViews)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = response.getWriter();
        outp.println(jsonFromJavaArrayList);
    }
}

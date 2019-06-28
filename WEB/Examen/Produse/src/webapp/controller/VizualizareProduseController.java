package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Produs;

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
import java.util.List;

@WebServlet("/viewProduse")
public class VizualizareProduseController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Produs> list = new ArrayList<>();
        String filtru = request.getParameter("filtru");
        System.out.println(filtru);
        String sqlStm;
        if (filtru == null || filtru.equals("")) {
            sqlStm = "SELECT id, nume, descriere, producator, pret, cantitate, path_poza FROM produse";
        } else {
            sqlStm = "SELECT id, nume, descriere," +
                    " producator, pret, cantitate, path_poza FROM produse " +
                    "WHERE nume LIKE CONCAT('%', '" + filtru + "' , '%') " +
                    "OR descriere LIKE CONCAT( '%', '" + filtru + "','%')";
        }
        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery(sqlStm);
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nume = rs.getString("nume");
                String descriere = rs.getString("descriere");
                String producator = rs.getString("producator");
                Integer pret = rs.getInt("pret");
                Integer cantitate = rs.getInt("cantitate");
                String path_poza = rs.getString("path_poza");
                list.add(Produs.builder()
                        .id(id)
                        .nume(nume)
                        .descriere(descriere)
                        .producator(producator)
                        .pret(pret)
                        .cantitate(cantitate)
                        .path_poza(path_poza)
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

package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Intrebare;
import webapp.model.Utilizator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getAllMessages")
public class MesajeController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        List<Intrebare> list = new ArrayList<>();

        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT id,username, mesaj, data_intr FROM intrebari");
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String username = rs.getString("username");
                String path_avatar = Utils.getPathAvatar(request,username);
                String msg = rs.getString("mesaj");
                Long data_intr = rs.getLong("data_intr");
                list.add(Intrebare.builder()
                        .id(id)
                        .username(username)
                        .data_intr(data_intr)
                        .mesaj(msg)
                        .path_avatar(path_avatar)
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

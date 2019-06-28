package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Intrebare;
import webapp.model.Raspuns;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getAllResponses")
public class RaspunsuriController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        List<Raspuns> list = new ArrayList<>();
        Integer idIntrebare = Integer.parseInt(request.getParameter("idIntrebare"));
        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            String sql = "SELECT id, mesaj, data_rasp, username FROM raspunsuri WHERE idIntrebare = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, idIntrebare);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String userName = rs.getString("username");
                String path_avatar = Utils.getPathAvatar(request,userName);
                String mesaj = rs.getString("mesaj");
                Long data_intr = rs.getLong("data_rasp");
                list.add(Raspuns.builder()
                        .id(id)
                        .username(userName)
                        .data_rasp(data_intr)
                        .mesaj(mesaj)
                        .path_avatar(path_avatar)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
        list.sort((x,y)-> (int) (y.getData_rasp()-x.getData_rasp()));
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = response.getWriter();
        outp.println(jsonFromJavaArrayList);
    }


}

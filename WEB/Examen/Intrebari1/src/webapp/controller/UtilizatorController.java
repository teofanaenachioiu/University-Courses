package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Utilizator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/getUtilizator")
public class UtilizatorController extends HttpServlet {
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//
//        String username = request.getParameter("username");
//        try {
//            Connection connection = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
//            String sql = "SELECT password,path_avatar FROM utilizatori WHERE username=?";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, username);
//            ResultSet rs = preparedStatement.executeQuery();
//            rs.next();
//
//            String parola = rs.getString("password");
//            String path = rs.getString("path_avatar");
//
//            Utilizator utilizator = Utilizator.builder()
//                    .username(username)
//                    .password(parola)
//                    .path_avatar(path)
//                    .build();
//            Gson gsonBuilder = new GsonBuilder().create();
//            String jsonFromJavaObject = gsonBuilder.toJson(utilizator);
//            PrintWriter outp = response.getWriter();
//            outp.println(jsonFromJavaObject);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}

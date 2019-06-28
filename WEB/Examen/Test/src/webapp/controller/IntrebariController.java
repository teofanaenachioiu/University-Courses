package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.model.Intrebare;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getIntrebari")
public class IntrebariController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute("start", System.currentTimeMillis());
        List<Intrebare> list = new ArrayList<>();
        Integer nrPag = Integer.valueOf(request.getParameter("nrPag"));
        Integer deUnde = (nrPag - 1) * 3;
        try {
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");
            String sql = "SELECT id, intrebare, r1,r2,r3,rc FROM intrebari LIMIT " + deUnde + ",3";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String intr = rs.getString("intrebare");
                String r1 = rs.getString("r1");
                String r2 = rs.getString("r2");
                String r3 = rs.getString("r3");
                Integer rc = rs.getInt("rc");
                list.add(Intrebare.builder()
                        .id(id)
                        .intr(intr)
                        .r1(r1)
                        .r2(r2)
                        .r3(r3)
                        .rc(rc)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = response.getWriter();
        outp.println(jsonFromJavaArrayList);

    }
}

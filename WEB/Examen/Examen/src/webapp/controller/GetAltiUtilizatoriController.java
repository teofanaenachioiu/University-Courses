package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.ApplicationManagement;
import webapp.model.Utilizator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/getAltiUtilizatori")
public class GetAltiUtilizatoriController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String username = req.getParameter("username");
//        List<Utilizator> list = ApplicationManagement.getAltiUtilizatori(username);
//        Gson gsonBuilder = new GsonBuilder().create();
//        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
//        PrintWriter outp = resp.getWriter();
//        outp.println(jsonFromJavaArrayList);

    }
}

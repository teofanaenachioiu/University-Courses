package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.ApplicationManagement;
import webapp.model.Castigator;
import webapp.model.Utilizator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/getCastigatori")
public class GetCastigatoriController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Castigator> list = ApplicationManagement.getCeiMaiTari();
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = resp.getWriter();
        outp.println(jsonFromJavaArrayList);

    }
}

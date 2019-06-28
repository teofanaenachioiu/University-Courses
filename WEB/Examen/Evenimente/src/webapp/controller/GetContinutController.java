package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.ApplicationManagement;
import webapp.model.Eveniment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.EventListener;
import java.util.List;

@WebServlet("/getContinut")
public class GetContinutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long dataEv = Long.valueOf(req.getParameter("dataEv"));
        List<Eveniment> list = ApplicationManagement.getContinut(dataEv);
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = resp.getWriter();
        outp.println(jsonFromJavaArrayList);

    }
}

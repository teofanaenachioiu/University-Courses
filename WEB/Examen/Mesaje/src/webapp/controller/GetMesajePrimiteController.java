package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.ApplicationManagement;
import webapp.model.Mesaj;
import webapp.model.Utilizator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/getMesajePrimite")
public class GetMesajePrimiteController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int nrPag = Integer.parseInt(req.getParameter("nrPag"));
        HttpSession session = req.getSession();
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        System.out.println(utilizator);
        List<Mesaj> list = ApplicationManagement.getMesajePrimite(utilizator.getUsername(), nrPag);
        System.out.println(list);
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = resp.getWriter();
        outp.println(jsonFromJavaArrayList);

    }
}

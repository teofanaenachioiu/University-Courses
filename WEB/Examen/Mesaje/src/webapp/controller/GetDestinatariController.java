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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/getDestinatari")
public class GetDestinatariController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        List<String> destinatari = ApplicationManagement.getDeastinatari(utilizator.getUsername());
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(destinatari);
        PrintWriter outp = resp.getWriter();
        outp.println(jsonFromJavaArrayList);


    }
}

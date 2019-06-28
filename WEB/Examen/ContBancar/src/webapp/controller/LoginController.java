package webapp.controller;

import webapp.ApplicationManagement;
import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long momCrr = System.currentTimeMillis();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ServletContext application = req.getSession().getServletContext();
        int penalizare = (int) application.getAttribute("penalizare");
        if ((long) application.getAttribute("lastLog") == 0) {
            application.setAttribute("lastLog", momCrr);
        }

        long lastLog = (long) application.getAttribute("lastLog");
        int nrLog = (int) application.getAttribute("nrLog");
        long diffLogs = momCrr - lastLog;
        System.out.println("Last log: "+lastLog);
        System.out.println("nrLog: "+nrLog);
        System.out.println("diff: "+diffLogs);

        Utilizator utilizator = null;
        if (diffLogs >= (penalizare * 1000) || nrLog <= 3) {
            utilizator = ApplicationManagement.autentificare(req.getSession(), username, password);
            System.out.println("Ma pot conecta");
        }
        else{
            System.out.println("Nu ma pot conecta");
        }

        if (utilizator != null) {
            application.setAttribute("penalizare", 0);
            application.setAttribute("nrLog", 0);
            resp.sendRedirect("indexAuth.jsp");
        } else {
            application.setAttribute("nrLog", nrLog + 1);
            if (nrLog == 3) {
                application.setAttribute("penalizare", 10);
            }
            if (nrLog > 3) {
                application.setAttribute("penalizare", penalizare * 2);
            }
            resp.sendRedirect("index.jsp");
        }
        application.setAttribute("lastLog", momCrr);
    }
}

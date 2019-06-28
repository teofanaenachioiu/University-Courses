package webapp.controller;

import webapp.ApplicationManagement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/adauga")
public class AdaugaEvenimentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String string_date = req.getParameter("dataEv");

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date date = null;
        try {
            date = formatter.parse(string_date);
            System.out.println(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dataEv = date.getTime();


        String ora = req.getParameter("oraEv");

        formatter = new SimpleDateFormat("mm:ss a");
        date = null;
        try {
            date = formatter.parse(ora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long oraEv = date.getTime();


        String descriere = req.getParameter("descriere");
        ApplicationManagement.adaugaEveniment(dataEv, oraEv, descriere);
        resp.sendRedirect("indexAuth.jsp");
    }
}

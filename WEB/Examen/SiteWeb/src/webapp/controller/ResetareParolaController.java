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

@WebServlet("/resetareParola")
public class ResetareParolaController  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String datan_string = req.getParameter("datan");
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(datan_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long datan = date.getTime();
        String nouaParola = req.getParameter("password");

        boolean schimbata = ApplicationManagement.schimbaParola(username, datan, nouaParola);
        if(schimbata){
            resp.sendRedirect("indexAuth.jsp");
        }
        else {
            resp.sendRedirect("login.jsp");
        }

    }
}

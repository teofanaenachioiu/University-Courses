package webapp.controller;

import webapp.ApplicationManagement;
import webapp.model.Utilizator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/setDetaliiUser")
public class SetDetaliiUserController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");

        String descriere = req.getParameter("descriere");
        System.out.println(descriere);
        System.out.println(utilizator);
        if (descriere.equals("") && utilizator.getDescriere() != null) {
            descriere = utilizator.getDescriere();
        }

        String datan_string = req.getParameter("datan");
        Long datan;
        if (utilizator.getDatan() != null) {
            datan = utilizator.getDatan();
        } else datan = Long.valueOf(0);

        if (!datan_string.equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
            Date date = null;
            try {
                date = formatter.parse(datan_string);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            datan = date.getTime();
        }

        ApplicationManagement.setDetalii(utilizator.getUsername(),descriere,datan);
        utilizator.setDatan(datan);
        utilizator.setDescriere(descriere);
        session.setAttribute("utilizator", utilizator);

        // cookies
        String culoareBg = req.getParameter("culoareBg");
        String culoareText = req.getParameter("culoareText");

        System.out.println(culoareBg);
        System.out.println(culoareText);

        Cookie bgColorCookie = new Cookie("culoareBg"+utilizator.getUsername(), culoareBg);
        bgColorCookie.setPath("/indexAuth.jsp");
        bgColorCookie.setPath("/settings.jsp");
        bgColorCookie.setPath("/top.jsp");
        resp.addCookie(bgColorCookie);

        Cookie textColorCookie = new Cookie("culoareText"+utilizator.getUsername(), culoareText);
        textColorCookie.setPath("/indexAuth.jsp");
        textColorCookie.setPath("/settings.jsp");
        textColorCookie.setPath("/top.jsp");
        resp.addCookie(textColorCookie);
        resp.sendRedirect("/indexAuth.jsp");
    }
}

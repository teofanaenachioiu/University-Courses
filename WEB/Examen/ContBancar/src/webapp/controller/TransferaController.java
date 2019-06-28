package webapp.controller;

import webapp.ApplicationManagement;
import webapp.model.Utilizator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/transferaBani")
public class TransferaController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer suma = Integer.valueOf(req.getParameter("suma"));
        Integer contDest = Integer.valueOf(req.getParameter("utilizatori"));
        HttpSession session = req.getSession();
        Utilizator utilizator = (Utilizator)session.getAttribute("utilizator");
        Integer contExp = utilizator.getCont();
        if(utilizator.getSuma()>=suma){
            ApplicationManagement.transferaBani(contExp, contDest, suma);
            utilizator.setSuma(utilizator.getSuma() - suma);
            session.setAttribute("utilizator", utilizator);
        }
        resp.sendRedirect("indexAuth.jsp");
    }
}

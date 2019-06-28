package webapp.controller;

import webapp.ApplicationManagement;
import webapp.EscapeUtils;
import webapp.model.Utilizator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/trimite")
public class TrimiteMesajController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subiect = EscapeUtils.escapeHtml(req.getParameter("subiect"));
        String userDest = EscapeUtils.escapeHtml(req.getParameter("userDest"));
        String continut = EscapeUtils.escapeHtml(req.getParameter("continut"));
        HttpSession session = req.getSession();
        Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
        ApplicationManagement.adaugaMesaj(utilizator.getUsername(), userDest, subiect,continut);
        resp.sendRedirect("trimiteMesaj.jsp");
    }
}

package webapp.controller;

import webapp.ApplicationManagement;
import webapp.model.Castigator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@WebServlet("/adaugaNumele")
public class AdaugaNumeleController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nume = req.getParameter("nume");
        List<Castigator> list = ApplicationManagement.getCeiMaiTari();
        Long timp = (Long) req.getSession().getAttribute("timp");
        if(list.size()>=3){
            Castigator castigatorUltim = list.get(list.size()-1);
            ApplicationManagement.stergeCastigator(castigatorUltim.getId());
        }
        ApplicationManagement.insereazaCastigator(nume, timp);
        req.getSession().setAttribute("timp",(long)0);
        //resp.sendRedirect("adaugaPoza.jsp");
    }
}

package webapp.controller;

import webapp.ApplicationManagement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adaugaNota")
public class AdaugaNotaController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Integer nota= Integer.valueOf(req.getParameter("nota"));
        ApplicationManagement.addVot (username, nota);
    }
}

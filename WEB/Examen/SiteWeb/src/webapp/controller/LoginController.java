package webapp.controller;

import webapp.ApplicationManagement;
import webapp.model.Utilizator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Utilizator utilizator = ApplicationManagement.autentificare(req.getSession(), username, password);
        if (utilizator == null){
            resp.sendRedirect("login.jsp");
        }
        else {
            resp.sendRedirect("indexAuth.jsp");
        }
    }
}

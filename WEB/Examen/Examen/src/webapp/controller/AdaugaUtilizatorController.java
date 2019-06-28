package webapp.controller;

import webapp.ApplicationManagement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adaugaUser")
public class AdaugaUtilizatorController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String username = req.getParameter("username");
//        String password = req.getParameter("password");
//        String repassword = req.getParameter("repassword");
//        if(password.equals(repassword)){
//            ApplicationManagement.addUser(username, password);
//            resp.sendRedirect("login.jsp");
//        }
//        resp.sendRedirect("inregistrare.jsp");
    }
}

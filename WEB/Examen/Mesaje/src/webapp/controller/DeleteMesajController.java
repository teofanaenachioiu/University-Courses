package webapp.controller;

import webapp.ApplicationManagement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteMesaj")
public class DeleteMesajController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idMesaj = Integer.parseInt(req.getParameter("idMesaj"));
        ApplicationManagement.deleteMesaj(idMesaj);
    }
}

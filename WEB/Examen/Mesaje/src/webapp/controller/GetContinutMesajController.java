package webapp.controller;

import webapp.ApplicationManagement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getContinutMesaj")
public class GetContinutMesajController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idMesaj = Integer.parseInt(req.getParameter("idMesaj"));

        String continut = ApplicationManagement.getConitnutMesaj(req, idMesaj);

        PrintWriter printWriter = resp.getWriter();
        printWriter.println(continut);
    }
}

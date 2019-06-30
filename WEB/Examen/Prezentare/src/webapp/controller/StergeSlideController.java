package webapp.controller;

import webapp.ApplicationManagement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/stergeSlide")
public class StergeSlideController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer idSlide = Integer.valueOf(req.getParameter("idSlide"));
        ApplicationManagement.stergeSlide(idSlide);
    }
}

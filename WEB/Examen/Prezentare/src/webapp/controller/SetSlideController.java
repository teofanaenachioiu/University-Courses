package webapp.controller;

import webapp.ApplicationManagement;
import webapp.model.Slide;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setSlide")
public class SetSlideController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer isSlide= Integer.valueOf(req.getParameter("idSlide"));
        Slide slide = ApplicationManagement.getSlide(isSlide);
        req.getSession().setAttribute("slide", slide);
    }
}

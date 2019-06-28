package webapp.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setKey")
public class SetKeywordContoller extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        req.getSession().setAttribute("keyword",keyword);
        System.out.println("inainte");
        System.out.println("dupa");
        resp.sendRedirect(req.getContextPath()+"/oras.jsp");
    }
}

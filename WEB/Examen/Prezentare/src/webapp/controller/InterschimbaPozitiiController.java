package webapp.controller;

import webapp.ApplicationManagement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/intershimbaPozitii")
public class InterschimbaPozitiiController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id1 = Integer.valueOf(req.getParameter("id1"));
        Integer id2 = Integer.valueOf(req.getParameter("id2"));
        ApplicationManagement.interschimbaPozitii(id1, id2);
    }
}

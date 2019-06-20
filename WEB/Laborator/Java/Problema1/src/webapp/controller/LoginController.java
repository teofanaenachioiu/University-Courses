package webapp.controller;

import webapp.model.DBManager;
import webapp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        RequestDispatcher rd = null;

        DBManager dbmanager = new DBManager();
        User user = dbmanager.authenticate(username, password);

        HttpSession session = request.getSession();

        if (user != null) {

            rd = request.getRequestDispatcher("/succes.jsp");

            session.setAttribute("user", user);

        } else {
            rd = request.getRequestDispatcher("/error.jsp");
        }
        rd.forward(request, response);
    }

}

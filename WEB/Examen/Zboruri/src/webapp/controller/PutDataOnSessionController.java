package webapp.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/putDataOnSession")
public class PutDataOnSessionController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long zbor = Long.valueOf(request.getParameter("dataZbor"));
        HttpSession session = request.getSession();
        session.setAttribute("dataZbor", zbor);
    }
}
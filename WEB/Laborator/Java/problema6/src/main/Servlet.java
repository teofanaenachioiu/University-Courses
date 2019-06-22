package main;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "Servlet", urlPatterns = {"/table"})
public class Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int game = Integer.parseInt(request.getParameter("game"));

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(GameMemory.getValues(game));
        out.flush();
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        int i = Integer.parseInt(request.getParameter("i"));
        int j = Integer.parseInt(request.getParameter("j"));
        int player = Integer.parseInt(request.getParameter("player"));
        int game = Integer.parseInt(request.getParameter("game"));

        if(GameMemory.isFree(game, i, j)) {
            if(player % 2 == 1) {
                GameMemory.player1Move(game, i, j);
            } else {
                GameMemory.player2Move(game, i, j);
            }
        }

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(GameMemory.getValues(game));
        out.flush();
    }
}

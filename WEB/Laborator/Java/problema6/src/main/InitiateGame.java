package main;

//import com.google.gson.Gson;
//import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "InitiateGame", urlPatterns = {"/init"})
public class InitiateGame extends Servlet{

    int currentPlayer = 0;
    int currentGameId = 1;
    Game currentGame;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        String resp = "";
        if(currentPlayer % 2 == 1) {
            resp = "wait";
        } else {
            resp = "ready";
        }
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(resp);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        currentPlayer++;
        String resp = "" + currentGameId + "," + currentPlayer;
        if(currentPlayer % 2 == 1) {
            currentGame = new Game(currentGameId, currentPlayer, -1);
        } else {
            currentGame.setPlayer2(currentPlayer);
            GameMemory.addGame(currentGame);
            currentGameId++;
        }

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print(resp);
    }
}

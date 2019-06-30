package webapp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import webapp.ApplicationManagement;
import webapp.model.Imagine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/getImagini")
public class GetImaginiController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("inceput", System.currentTimeMillis());

        List<Imagine> listImagini = ApplicationManagement.getImagini();
        List<String> listDescrieri = ApplicationManagement.getDescrieri();
        Collections.shuffle(listDescrieri);
        Collections.shuffle(listImagini);
        List<Imagine> list = new ArrayList<>();
        for (int i = 0; i < listImagini.size(); i++) {
            list.add(Imagine.builder()
                    .descriere(listDescrieri.get(i))
                    .path_img(listImagini.get(i).getPath_img())
                    .id(listImagini.get(i).getId())
                    .build());
        }
        session.setAttribute("nrImagini", list.size());
        session.setAttribute("nrGhicite", 0);
        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromJavaArrayList = gsonBuilder.toJson(list);
        PrintWriter outp = resp.getWriter();
        outp.println(jsonFromJavaArrayList);

    }
}

package webapp.controller;

import webapp.ApplicationManagement;
import webapp.model.Castigator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/verificaPerechea")
public class VerificaPerecheaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.valueOf(req.getParameter("id"));
        String descriere = req.getParameter("descriere");
        boolean corect = ApplicationManagement.verificaPerechea(id, descriere);
        PrintWriter printWriter = resp.getWriter();
        if (corect) {
            HttpSession session = req.getSession();
            Integer nrGhiciteAnterior = (Integer) session.getAttribute("nrGhicite");
            session.setAttribute("nrGhicite", nrGhiciteAnterior+1);
            Integer nrGhiciteAcum = nrGhiciteAnterior+1;

            Integer nrTotal = (Integer) session.getAttribute("nrImagini");
            System.out.println("NR toatal "+nrTotal);
            System.out.println("NR ghicite "+nrGhiciteAcum);
            if(nrGhiciteAcum.equals(nrTotal)){
                Long inceput = (Long) session.getAttribute("inceput");
                Long timp = System.currentTimeMillis() - inceput;

                List<Castigator> lista = ApplicationManagement.getCeiMaiTari();
                Castigator castigatorUltim= lista.get(lista.size()-1);
                System.out.println(timp);
                if (castigatorUltim.getTimp()>timp){
                   session.setAttribute("timp",(long) timp);
                }
                else {
                    session.setAttribute("timp",(long)0);
                }
                Long timpppp = (Long) session.getAttribute("timp");
                System.out.println(timpppp);
                System.out.println("GATA");
                printWriter.print(2);
            }
           else {
                System.out.println("CORECT");
                printWriter.print(1);
            }
        } else{
            System.out.println("GRESIT");
            printWriter.print(0);
        }
    }
}

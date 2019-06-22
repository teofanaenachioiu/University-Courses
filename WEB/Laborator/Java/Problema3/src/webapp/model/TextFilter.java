package webapp.model;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

public class TextFilter implements Filter {
    String outText = null;

    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) {
        System.out.println("in filter");
        ArrayList<String> regexuri = new ArrayList<>();
        try {
            Connection con = (Connection) request.getServletContext().getAttribute("conexiune");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT regex FROM expresii");
            while(rs.next()){
                String msg = rs.getString("regex");
                regexuri.add(msg);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        ArrayList<Mesaj> list = new ArrayList<>();
        try {
            Connection con = (Connection) request.getServletContext().getAttribute("conexiune");
            Statement query = con.createStatement();
            ResultSet rs = query.executeQuery("SELECT id, message FROM messages");
            while(rs.next()){
                Integer id = rs.getInt("id");
                String msg = rs.getString("message");
                list.add(new Mesaj(id,msg));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        try {
            PrintWriter out = response.getWriter();
            int crt =0;
            out.println("<table>");
            for(Mesaj m: list){
                crt+=1;
                String requestedText = m.getMsg();
                for(String reg: regexuri){
                    requestedText = requestedText.replaceAll(reg, this.outText);
                }
                System.out.println(requestedText);
                out.println("<tr><th> MESAJ "+ crt+":  " + "</th></tr>");
                out.println( "<tr><td>" +requestedText+ "</td> </tr>");
            }
            out.println("</table>");

            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void init(FilterConfig filterConfig) {
        this.outText = filterConfig.getInitParameter("outText");
    }

    public void destroy() {
    }

}


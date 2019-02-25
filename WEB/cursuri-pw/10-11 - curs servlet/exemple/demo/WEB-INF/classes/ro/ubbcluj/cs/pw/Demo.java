package ro.ubbcluj.cs.pw;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Demo extends HttpServlet {
  

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {


    PrintWriter out = response.getWriter();
    out.println("Happy New Year!!!");
  }
}

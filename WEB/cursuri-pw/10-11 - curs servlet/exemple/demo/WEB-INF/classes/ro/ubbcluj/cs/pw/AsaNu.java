package ro.ubbcluj.cs.pw;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AsaNu extends HttpServlet {

  String user = null;
  String password = null;

  public boolean verifica() {
    // verifica daca userul si parola sunt corecte
    return true;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    user = request.getParameter("user");
    password = request.getParameter("password");

    if (verifica()) {
      // ...  
    }
    else {
      // ...
    }
  }

}

package webapp.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

@WebServlet("/incarcarePoza")
public class IncarcarePozaController extends HttpServlet {
    private String filePath;
    private File file;
    private String descriere = null;
    private String filename = null;


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        filePath = getServletContext().getInitParameter("file-upload");
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File("c:\\temp"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    System.out.println(name);
                    descriere = item.getString();
                    System.out.println(descriere);
                } else if (!item.isFormField()) {
                    System.out.println("aici intra..");
                    System.out.println(item);
                    System.out.println("dupa");
                    filename = item.getName();
                    System.out.println("dupa filename");
                    System.out.println(filename);
                    if (filename.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + filename.substring(filename.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + filename.substring(filename.lastIndexOf("\\") + 1));
                    }
                    item.write(file);
                }
            }
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");
            HttpSession session = request.getSession();
            Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
            String sql = "INSERT INTO incarcari(username, descriere, path_poza) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, utilizator.getUsername());
            preparedStatement.setString(2, descriere);
            preparedStatement.setString(3, "/avatare/" + filename);
            preparedStatement.execute();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        response.sendRedirect("indexAuth.jsp");
    }
}

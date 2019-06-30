package webapp.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/adaugaImagine")
public class AdaugaImagineController extends HttpServlet {
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
                    String value = item.getString();
                    switch (name) {
                        case "descriere": {
                            descriere = value;
                            break;
                        }
                    }
                } else if (!item.isFormField()) {
                    filename = item.getName();
                    if (filename.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + filename.substring(filename.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + filename.substring(filename.lastIndexOf("\\") + 1));
                    }
                    item.write(file);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");
            String sql = "INSERT INTO imagini(descriere, path_img) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, descriere);
            filename = "./uploads/" + filename;
            preparedStatement.setString(2, filename);
            preparedStatement.execute();
            response.sendRedirect("adaugaPoza.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}

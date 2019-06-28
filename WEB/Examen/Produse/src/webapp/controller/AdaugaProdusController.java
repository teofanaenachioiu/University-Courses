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

@WebServlet("/adaugaProdus")
public class AdaugaProdusController extends HttpServlet {
    private String filePath;
    private File file;
    private String nume = null;
    private String descriere = null;
    private String producator = null;
    private Integer pret = null;
    private Integer cantitate = null;
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
                        case "nume": {
                            nume = value;
                            break;
                        }
                        case "descriere": {
                            descriere = value;
                            break;
                        }
                        case "producator": {
                            producator = value;
                            break;
                        }
                        case "pret": {
                            pret = Integer.valueOf(value);
                            break;
                        }
                        case "cantitate": {
                            cantitate = Integer.valueOf(value);
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
            String sql = "INSERT INTO produse(nume, descriere, producator, pret, cantitate, path_poza) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nume);
            preparedStatement.setString(2, descriere);
            preparedStatement.setString(3, producator);
            preparedStatement.setInt(4, pret);
            preparedStatement.setInt(5, cantitate);
            filename = "./uploads/" + filename;
            preparedStatement.setString(6, filename);
            preparedStatement.execute();
            response.sendRedirect("indexAdmin.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}

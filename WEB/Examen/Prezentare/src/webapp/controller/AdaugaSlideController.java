package webapp.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import webapp.ApplicationManagement;

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

@WebServlet("/adaugaSlide")
public class AdaugaSlideController extends HttpServlet {
    private String filePath;
    private File file;
    private String titlu = null;
    private String text = null;
    private String layout = null;
    private Integer nrSecunde = null;
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
                        case "titlu": {
                            titlu = value;
                            break;
                        }
                        case "text": {
                            text = value;
                            break;
                        }
                        case "layout": {
                            layout = value;
                            break;
                        }
                        case "nrSecunde": {
                            System.out.println(value);
                            nrSecunde = Integer.valueOf(value);
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
            System.out.println(titlu);
            System.out.println(text);
            System.out.println(filename);
            System.out.println(nrSecunde);
            System.out.println(layout);
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");
            System.out.println(connection);
            String sql = "INSERT INTO slideuri(titlu, text, path_img, nrSecunde, layout,nrOrd) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, titlu);
            preparedStatement.setString(2, text);
            filename = "./uploads/" + filename;
            preparedStatement.setString(3, filename);
            preparedStatement.setInt(4, nrSecunde);
            preparedStatement.setString(5, layout);
            preparedStatement.setInt(6, ApplicationManagement.numarSlideuri()+1);
            preparedStatement.execute();
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

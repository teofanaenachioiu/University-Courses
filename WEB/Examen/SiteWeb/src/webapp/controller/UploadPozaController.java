package webapp.controller;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import webapp.model.Utilizator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

@WebServlet(
        name = "file-upload",
        urlPatterns = {"/uploadPhoto"}
)
public class UploadPozaController extends HttpServlet {
        private boolean isMultipart;
        private String filePath;
        private File file ;

        public void init( ){
            filePath = getServletContext().getInitParameter("file-upload");
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            isMultipart = ServletFileUpload.isMultipartContent(request);
            response.setContentType("text/html");
            java.io.PrintWriter out = response.getWriter( );

            DiskFileItemFactory factory = new DiskFileItemFactory();

            factory.setRepository(new File("c:\\temp"));

            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List fileItems = upload.parseRequest(request);

                Iterator i = fileItems.iterator();

                while ( i.hasNext () ) {
                    FileItem fi = (FileItem)i.next();
                    if ( !fi.isFormField () ) {
                        String fileName = fi.getName();

                        if( fileName.lastIndexOf("\\") >= 0 ) {
                            file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                        } else {
                            file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                        }
                        fi.write( file ) ;
                        updateazaAvatarul(request, "./avatare/"+fileName);
                        response.sendRedirect("indexAuth.jsp");
                    }
                    break;
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }


        private void updateazaAvatarul(HttpServletRequest request, String filename)  {
            HttpSession session = request.getSession();
            Utilizator utilizator = (Utilizator) session.getAttribute("utilizator");
            try {
                ServletContext application = request.getSession().getServletContext();
                Connection connection = (Connection) application.getAttribute("conexiune");

                String sql = "UPDATE utilizatori SET path_avatar = ? WHERE username = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, filename);
                preparedStatement.setString(2, utilizator.getUsername());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}

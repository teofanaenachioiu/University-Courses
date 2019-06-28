package webapp.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

@WebServlet("/adaugaViewNou")
public class AdaugaVizualizareNouaController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer idProdus = Integer.valueOf(request.getParameter("id"));
        System.out.println(idProdus);
        int luna = Calendar.getInstance().get(Calendar.MONTH) + 1;
        System.out.println(luna);
        try {
            Connection con = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            String sqlSelect = "SELECT nrViews FROM popularitate WHERE idProdus = ? and nrLuna = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sqlSelect);
            preparedStatement.setInt(1, idProdus);
            preparedStatement.setInt(2, luna);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int nrViews = rs.getInt("nrViews");
                String sqlStm = "UPDATE popularitate SET nrViews = ? WHERE idProdus = ? and nrLuna = ?";
                preparedStatement = con.prepareStatement(sqlStm);
                preparedStatement.setInt(1, nrViews + 1);
                preparedStatement.setInt(2, idProdus);
                preparedStatement.setInt(3, luna);
                preparedStatement.execute();
            }
            else {

                String sqlStm = "INSERT INTO popularitate(nrViews,nrLuna,idProdus) VALUES (?, ?, ?)";
                preparedStatement = con.prepareStatement(sqlStm);
                preparedStatement.setInt(1,  1);
                preparedStatement.setInt(2, luna);
                preparedStatement.setInt(3, idProdus);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}

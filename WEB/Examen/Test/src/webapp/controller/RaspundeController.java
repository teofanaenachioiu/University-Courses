package webapp.controller;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

@WebServlet("/raspunde")
public class RaspundeController extends HttpServlet {
    private void insereazaRaspuns(Integer r, Integer idIntrebare, Integer idUtilizator, Connection connection) throws SQLException {
        String sql;
        PreparedStatement preparedStatement;
        ResultSet rs;
        Integer rc;
        if (r != null) {
            sql = "SELECT rc FROM intrebari WHERE id = " + idIntrebare;
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                rc = rs.getInt("rc");
                sql = "INSERT INTO raspunsuri (idIntrebare, idUtilizator,rasp, corect ) VALUES (?,?,?,?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idIntrebare);
                preparedStatement.setInt(2, idUtilizator);
                preparedStatement.setInt(3, r);
                preparedStatement.setBoolean(4, r.equals(rc));
                preparedStatement.execute();
            }
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Long now = System.currentTimeMillis();
        HttpSession session = request.getSession();
        Long start = (Long) session.getAttribute("start");

        Integer nrPag = Integer.valueOf(request.getParameter("nrPag"));
        if (nrPag.equals(1)) {
            String nume = request.getParameter("nume");
            String email = request.getParameter("email");
            try {
                adaugaUser(nume, email, request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Integer r1 = Integer.valueOf(request.getParameter("r1"));
        Integer r2 = Integer.valueOf(request.getParameter("r2"));
        Integer r3 = Integer.valueOf(request.getParameter("r3"));

        Integer deUnde = (nrPag - 1) * 3 + 1;
        if ((now - start) > 10000) {
            System.out.println("Timp depasit");
            r1 = 0;
            r2 = 0;
            r3 = 0;
        }
        try {
            ServletContext application = request.getSession().getServletContext();
            Connection connection = (Connection) application.getAttribute("conexiune");
            Integer idUtilizator = (Integer) application.getAttribute("idUtilizator");
            insereazaRaspuns(r1, deUnde,idUtilizator, connection);
            insereazaRaspuns(r2, deUnde + 1,idUtilizator, connection);
            insereazaRaspuns(r3, deUnde + 2,idUtilizator, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adaugaUser(String nume, String email, HttpServletRequest request) throws SQLException {
        ServletContext application = request.getSession().getServletContext();
        Connection connection = (Connection) application.getAttribute("conexiune");
        String sql;
        PreparedStatement preparedStatement;
        ResultSet rs;
        sql = "INSERT INTO utilizatori(nume, email, punctaj) VALUES (?,?,?)";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, nume);
        preparedStatement.setString(2, email);
        preparedStatement.setInt(3, 0);
        preparedStatement.executeUpdate();

        rs = preparedStatement.getGeneratedKeys();
        Integer key = 0;
        if (rs.next()) {
            key = rs.getInt(1);
        }
        HttpSession session = request.getSession();
        session.setAttribute("idUtilizator",key);
    }
}

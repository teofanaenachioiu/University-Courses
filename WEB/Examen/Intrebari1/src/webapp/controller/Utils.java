package webapp.controller;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Utils {
    public static String getPathAvatar(HttpServletRequest request, String username) {
        try {
            Connection connection = (Connection) request.getSession().getServletContext().getAttribute("conexiune");
            String sql = "SELECT password,path_avatar FROM utilizatori WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            return rs.getString("path_avatar");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

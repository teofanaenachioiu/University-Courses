package repository;

import domain.User;
import repository.jdbcUtils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Repository {
    private JdbcUtils dbUtils;

    public Repository(Properties properties) {
        dbUtils=new JdbcUtils(properties);
    }

    public User findOne(String string) {
        Connection con=this.dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Users where username=?")){
            preStmt.setString(1,string);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String username = result.getString("username");
                    String parola = result.getString("parola");
                    return new User(username,parola);
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }
}

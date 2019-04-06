package repository;

import model.TipUser;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository implements IRepositoryUser {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();
    public UserRepository(Properties props){
        logger.info("Initializing UserRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Users")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public String save(User entity) {
        logger.traceEntry("saving user {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Users values (?,?,?)")){
            preStmt.setString(1,entity.getID());
            preStmt.setString(2,entity.getHash());
            preStmt.setString(3,entity.getTip().toString());
            int result=preStmt.executeUpdate();
            if(result==0) throw new RepositoryException("Error: Nu s-a putut adauga userul!");
            return entity.getID();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void delete(String s) {
        logger.traceEntry("deleting user with {}",s);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Users where username=?")){
            preStmt.setString(1,s);
            int result=preStmt.executeUpdate();
            if(result==0) throw new RepositoryException("Error: Nu s-a putut sterge userul!");
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(String s, User entity) {
        logger.traceEntry("updating user with {}",s);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("update Users " +
                "set hash=?, tip=?" +
                "where username=?")){
            preStmt.setString(3,s);
            preStmt.setString(1,entity.getHash());
            preStmt.setString(2,entity.getTip().toString());
            int result=preStmt.executeUpdate();
            if(result==0) throw new RepositoryException("Error: Nu s-a putut actualiza userul!");
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public User findOne(String s) {
        logger.traceEntry("finding user with id {} ",s);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Users where username=?")){
            preStmt.setString(1,s);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String username = result.getString("username");
                    String hash = result.getString("hash");
                    String tip = result.getString("tip");
                    User user=new User(username,hash, TipUser.valueOf(tip));
                    logger.traceExit(user);
                    return user;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No user found with id {}", s);

        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<User> users=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Users")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String username = result.getString("username");
                    String hash = result.getString("hash");
                    String tip = result.getString("tip");
                    User user=new User(username,hash, TipUser.valueOf(tip));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(users);
        return users;
    }
}

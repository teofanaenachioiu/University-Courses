package tasks.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by grigo on 3/2/17.
 */
@Component
public class SortingTaskJdbcRepository implements SortingTaskRepository {
    private JdbcUtils dbUtils;

    @Autowired
    public SortingTaskJdbcRepository(Properties props){
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from SortingTasks")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(SortingTask entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into SortingTasks values (?,?,?,?,?)")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getDesc());
            preStmt.setInt(3,entity.getNrElem());
            preStmt.setString(5,entity.getAlg().name());
            preStmt.setString(4,entity.getOrder().name());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from SortingTasks where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, SortingTask entity) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update SortingTasks set descriere=?, Elems=?, orderC=?, algoritm=? where id=?")){
            preStmt.setString(1,entity.getDesc());
            preStmt.setInt(2,entity.getNrElem());
            preStmt.setString(3,entity.getOrder().toString());
            preStmt.setString(4,entity.getAlg().toString());
            preStmt.setInt(5,integer);
            preStmt.executeUpdate();
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }


    @Override
    public SortingTask findOne(Integer integer) {
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from SortingTasks where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String desc = result.getString("descriere");
                    int elems = result.getInt("Elems");
                    SortingOrder order = SortingOrder.valueOf(result.getString("orderC"));
                    SortingAlgorithm algo = SortingAlgorithm.valueOf(result.getString("algoritm"));
                    SortingTask task = new SortingTask(id, desc, algo, order, elems);
                    return task;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public Iterable<SortingTask> findAll() {
        Connection con=dbUtils.getConnection();
        List<SortingTask> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from SortingTasks")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String desc = result.getString("descriere");
                    int elems = result.getInt("Elems");
                    SortingOrder order = SortingOrder.valueOf(result.getString("orderC"));
                    SortingAlgorithm algo = SortingAlgorithm.valueOf(result.getString("algoritm"));
                    SortingTask task = new SortingTask(id, desc, algo, order, elems);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return tasks;
    }


    @Override
    public Iterable<SortingTask> filterBy(SortingOrder orderC) {
        Connection con=dbUtils.getConnection();
        List<SortingTask> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from SortingTasks where orderC=?")) {
            preStmt.setString(1,orderC.name());
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String desc = result.getString("descriere");
                    int elems = result.getInt("Elems");
                    SortingAlgorithm algo = SortingAlgorithm.valueOf(result.getString("algoritm"));
                    SortingTask task = new SortingTask(id, desc, algo, orderC, elems);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return tasks;
    }

    @Override
    public Iterable<SortingTask> filterBy(SortingAlgorithm algo) {
        Connection con=dbUtils.getConnection();
        List<SortingTask> tasks=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from SortingTasks where algoritm=?")) {
            preStmt.setString(1,algo.name());
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String desc = result.getString("descriere");
                    int elems = result.getInt("Elems");
                    SortingOrder order = SortingOrder.valueOf(result.getString("orderC"));
                    SortingTask task = new SortingTask(id, desc, algo, order, elems);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return tasks;
    }
}

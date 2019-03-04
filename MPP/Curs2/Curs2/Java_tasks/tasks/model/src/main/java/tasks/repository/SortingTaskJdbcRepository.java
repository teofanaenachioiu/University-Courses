package tasks.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by grigo on 3/2/17.
 */
public class SortingTaskJdbcRepository implements IRepository<Integer,SortingTask> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public SortingTaskJdbcRepository(Properties props){
        logger.info("Initializing SortingTaskRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from SortingTasks")) {
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
    public void save(SortingTask entity) {
        logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into SortingTasks values (?,?,?,?,?)")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getDesc());
            preStmt.setInt(3,entity.getNrElem());
            preStmt.setString(5,entity.getAlg().name());
            preStmt.setString(4,entity.getOrder().name());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting task with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from SortingTasks where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, SortingTask entity) {
        //To do
    }

    @Override
    public SortingTask findOne(Integer integer) {
        logger.traceEntry("finding task with id {} ",integer);
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
                    logger.traceExit(task);
                    return task;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<SortingTask> findAll() {
        logger.traceEntry();
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
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(tasks);
        return tasks;
    }



}

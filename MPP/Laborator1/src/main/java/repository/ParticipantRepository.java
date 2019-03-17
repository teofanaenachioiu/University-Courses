package repository;

import javafx.util.Pair;
import model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantRepository implements IRepositoryParticipant{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();
    public ParticipantRepository(Properties props){
        logger.info("Initializing ParticipantRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Participanti")) {
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
    public Integer save(Participant entity) {
        logger.traceEntry("saving participant {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Participanti(nume, varsta) values (?,?)")){
            preStmt.setString(1,entity.getNume());
            preStmt.setInt(2,entity.getVarsta());
            int result=preStmt.executeUpdate();
            if (result==0)
                throw new RepositoryException("Error: Nu s-a putut adauga participantul!");
            try(PreparedStatement preStmt1=con.prepareStatement("select id from Participanti order by id desc limit 1")) {
                try(ResultSet result1 = preStmt1.executeQuery()) {
                    if (result1.next()) {
                        return result1.getInt("id");
                    }
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting participant with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Participanti where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
            if(result == 0)
                throw new RepositoryException("Error: Nu s-a putut sterge participantul!");
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Participant entity) {
        logger.traceEntry("updating participant with {}",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("update Participanti " +
                "set nume=?, varsta=?" +
                "where id=?")){
            preStmt.setInt(3,integer);
            preStmt.setString(1,entity.getNume());
            preStmt.setInt(2,entity.getVarsta());
            int result=preStmt.executeUpdate();
            if(result == 0)
                throw new RepositoryException("Error: Nu s-a putut actualiza participantul!");
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Participant findOne(Integer integer) {
        logger.traceEntry("finding participant with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    Integer id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer varsta = result.getInt("varsta");
                    Participant participant=new Participant(id,nume,varsta);
                    logger.traceExit(participant);
                    return participant;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No participant found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Participant> participanti=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Participanti")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer varsta = result.getInt("varsta");
                    Participant participant=new Participant(id,nume,varsta);
                    participanti.add(participant);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(participanti);
        return participanti;
    }
}

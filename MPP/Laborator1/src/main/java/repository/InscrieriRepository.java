package repository;

import javafx.util.Pair;
import model.Inscriere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class InscrieriRepository implements IRepository<Pair<Integer,Integer>, Inscriere>{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();
    public InscrieriRepository(Properties props) {
        logger.info("Initializing ProbaRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Inscrieri")) {
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
    public void save(Inscriere entity) {
        logger.traceEntry("saving inscriere {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Inscrieri values (?,?)")){
            preStmt.setInt(1,entity.getID().getKey());
            preStmt.setInt(2,entity.getID().getValue());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Pair<Integer, Integer> integerIntegerPair) {
        logger.traceEntry("deleting inscriere with {}", integerIntegerPair.getKey()+","+integerIntegerPair.getValue());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Probe where idParticipant=? and idProba=?")){
            preStmt.setInt(1,integerIntegerPair.getKey());
            preStmt.setInt(2,integerIntegerPair.getValue());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Pair<Integer, Integer> integerIntegerPair, Inscriere entity) {
        logger.traceEntry("updating inscriere with {}",integerIntegerPair);
        Connection con=dbUtils.getConnection();

        logger.traceExit();
    }

    @Override
    public Inscriere findOne(Pair<Integer, Integer> integerIntegerPair) {
        logger.traceEntry("finding inscriere with id {} ",integerIntegerPair);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe where idParticipant=? and idProba=?")){
            preStmt.setInt(1,integerIntegerPair.getKey());
            preStmt.setInt(2,integerIntegerPair.getValue());
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    Integer idParticipant = result.getInt("idParticipant");
                    Integer idProba = result.getInt("idProba");
                    Inscriere inscriere=new Inscriere(idParticipant,idProba);
                    logger.traceExit(inscriere);
                    return inscriere;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No inscriere found with id {}", integerIntegerPair);

        return null;
    }

    @Override
    public Iterable<Inscriere> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Inscriere> inscrieri=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Inscrieri")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer idParticipant = result.getInt("idParticipant");
                    Integer idProba = result.getInt("idProba");
                    Inscriere inscriere=new Inscriere(idParticipant,idProba);
                    inscrieri.add(inscriere);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(inscrieri);
        return inscrieri;
    }
}

package repository;

import model.Categorie;
import model.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProbaRepository implements IRepositoryProba{
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();
    public ProbaRepository(Properties props){
        logger.info("Initializing ProbaRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }
    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Probe")) {
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
    public Integer save(Proba entity) {
        logger.traceEntry("saving proba {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Probe(denumire, categorie) values (?,?)")){
            preStmt.setString(1,entity.getDenumire());
            preStmt.setString(2,entity.getCatg().toString());
            int result=preStmt.executeUpdate();
            if(result==0) throw new RepositoryException("Error: Nu s-a putut salva proba!");
            try(PreparedStatement preStmt1=con.prepareStatement("select top 1 id from Probe order by id desc")) {
                try(ResultSet result1 = preStmt.executeQuery()) {
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
        logger.traceEntry("deleting proba with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Probe where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
            if(result==0) throw new RepositoryException("Error: Nu s-a putut sterge proba!");
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Proba entity) {
        logger.traceEntry("updating proba with {}",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("update Probe " +
                "set denumire=?, categorie=?" +
                "where id=?")){
            preStmt.setInt(3,integer);
            preStmt.setString(1,entity.getDenumire());
            preStmt.setString(2,entity.getCatg().toString());
            int result=preStmt.executeUpdate();
            if(result==0) throw new RepositoryException("Error: Nu s-a putut actualiza proba!");
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Proba findOne(Integer integer) {
        logger.traceEntry("finding proba with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    Integer id = result.getInt("id");
                    String descriere = result.getString("denumire");
                    String categorie = result.getString("categorie");
                    Proba proba=new Proba(id,descriere,Categorie.valueOf(categorie));
                    logger.traceExit(proba);
                    return proba;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No proba found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Proba> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Proba> probe=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Probe")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String descriere = result.getString("denumire");
                    String categorie = result.getString("categorie");
                    Proba proba=new Proba(id,descriere,Categorie.valueOf(categorie));
                    probe.add(proba);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(probe);
        return probe;
    }

    @Override
    public Iterable<Categorie> listaCategorii() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Categorie> categorii=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select distinct categorie from Probe")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String categorie = result.getString("categorie");
                    categorii.add(Categorie.valueOf(categorie));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(categorii);
        return categorii;
    }

    @Override
    public Iterable<String> listaProbeNume() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<String> probe=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select distinct denumire from Probe")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String denumire = result.getString("denumire");
                    probe.add(denumire);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(probe);
        return probe;
    }
}

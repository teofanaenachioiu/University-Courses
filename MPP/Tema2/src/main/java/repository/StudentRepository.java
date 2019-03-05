package repository;

import domain.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.jdbcUtils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StudentRepository implements IRepository<Integer, Student> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public StudentRepository(Properties props){
        logger.info("Initializing StudentRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Studenti")) {
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
    public void save(Student entity) {
        logger.traceEntry("saving student {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into STUDENTI values (?,?,?,?)")){
            preStmt.setInt(1,entity.getNrMatricol());
            preStmt.setString(2,entity.getNume());
            preStmt.setString(3,entity.getFacultate());
            preStmt.setInt(4,entity.getAn());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting student with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from studenti where nrMatricol=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Student entity) {
        logger.traceEntry("updating student with {}",integer);
        Connection con=dbUtils.getConnection();
        Student student=findOne(integer);
        if(student==null)
            return;
        Integer newNrMatricol=entity.getNrMatricol();
        String newNume=entity.getNume();
        String newFacultate=entity.getFacultate();
        Integer newAn=entity.getAn();

        try(PreparedStatement preStmt=con.prepareStatement("update Studenti " +
                "set nrMatricol = ?, nume=?, facultate=?, an=?" +
                "where nrMatricol=?")){
            preStmt.setInt(5,integer);
            preStmt.setInt(1,newNrMatricol);
            preStmt.setString(2,newNume);
            preStmt.setString(3,newFacultate);
            preStmt.setInt(4,newAn);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Student findOne(Integer integer) {
        logger.traceEntry("finding student with nrMAtricol {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from studenti where nrMatricol=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int nrMatricol = result.getInt("nrMatricol");
                    String nume = result.getString("nume");
                    String facultate = result.getString("facultate");
                    int an = result.getInt("an");
                    Student student = new Student(nrMatricol,nume,facultate,an);
                    logger.traceExit(student);
                    return student;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No student found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Student> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Student> students=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from studenti")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int nrMatricol = result.getInt("nrMatricol");
                    String nume = result.getString("nume");
                    String facultate = result.getString("facultate");
                    int an = result.getInt("an");
                    Student student = new Student(nrMatricol,nume,facultate,an);
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(students);
        return students;
    }



}
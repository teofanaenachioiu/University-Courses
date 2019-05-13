package repository;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.util.*;

public class UserRepository implements IRepositoryUser {

    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;

    public UserRepository() {

    }

    public void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Override
    public int size() {
        logger.traceEntry();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                int count = ((Long) session.createQuery("select count(*) from User").uniqueResult()).intValue();
                tx.commit();
                return count;
            } catch (RuntimeException ex) {
                logger.error(ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return 0;
    }

    @Override
    public String save(User entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            System.out.println("Session: " + session);
            try {
                System.out.println("in try0");
                tx = session.beginTransaction();
                System.out.println("in try1");
                if (findOne(entity.getID()) != null) {
                    return null;
                }
                session.save(entity);
                System.out.println("in try2");
                tx.commit();
                System.out.println("in try3");
                return entity.getID();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println("in catch");
            }
        }
        return null;
    }

    @Override
    public void delete(String s) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "DELETE FROM User " +
                        "WHERE username like :usr_id";
                Query query = session.createQuery(hql);
                query.setParameter("usr_id", s);
                int result = query.executeUpdate();
                System.out.println("Rows affected: " + result);

                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void update(String s, User entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query qry = session.createQuery("update User set hash=:parola, tip =:t where username=:id");
                qry.setParameter("parola", entity.getHash());
                qry.setParameter("t", entity.getTip());
                qry.setParameter("id", s);

                qry.executeUpdate();
                tx.commit();

            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public User[] findOneByTip(String tip) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "FROM User WHERE tip =:tipUser";

                Query query = session.createQuery(hql);
                query.setParameter("tipUser", tip);
                List us = query.getResultList();
                User[] users = new User[us.size()];
                int index = 0;
                for (Object user : us
                ) {
                    users[index] = (User) user;
                    index += 1;
                }
                tx.commit();
                return users;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println("---------------------- ERROR ---------------------");
                System.out.println(ex);
            }
        }
        return null;
    }

    @Override
    public User findOne(String s) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("Am intrat1");
                String hql = "FROM User WHERE username = :id";

                Query query = session.createQuery(hql);
                query.setParameter("id", s);
                User user = (User) query.getResultList().get(0);

                tx.commit();
                return user;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                System.out.println("---------------------- ERROR ---------------------");
                System.out.println(ex);
            }
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<User> from_users =
                        session.createQuery("from User", User.class).
                                //  setFirstResult(1).setMaxResults(5).
                                        list();
                System.out.println(from_users.size() + " user(s) found:");
                tx.commit();
                return from_users;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();

            }
        }
        return null;
    }

    /*@Override
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
    }*/
}

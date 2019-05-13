package repository;

import javafx.util.Pair;
import model.Inscriere;
import model.Participant;
import model.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class InscriereRepository implements IRepositoryInscriere {
    private JdbcUtils dbUtils;

    private static final Logger logger = LogManager.getLogger();

    public InscriereRepository() {
        dbUtils = new JdbcUtils();
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Inscrieri")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        return 0;
    }

    @Override
    public Pair<Integer, Integer> save(Inscriere entity) {
        long nrProbe = StreamSupport
                .stream(findProbeDupaParticipant(entity.getID().getKey()).spliterator(), false)
                .count();
        if (nrProbe >= 2) throw new RepositoryException("Participantul e deja inscris la doua probe");

        logger.traceEntry("saving inscriere {} ", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Inscrieri values (?,?,?,?)")) {
            preStmt.setInt(1, entity.getID().getKey());
            preStmt.setInt(2, entity.getID().getValue());
            Date date = Date.valueOf(entity.getData());
            preStmt.setDate(3, date);
            preStmt.setString(4, entity.getUsernameOperator());
            int result = preStmt.executeUpdate();
            if (result == 0)
                throw new RepositoryException("Error: Nu s-a putut adauga inscrierea!");
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
        return new Pair<>(entity.getID().getKey(), entity.getID().getValue());
    }

    @Override
    public void delete(Pair<Integer, Integer> integerIntegerPair) {
        logger.traceEntry("deleting inscriere with {}", integerIntegerPair.getKey() + "," + integerIntegerPair.getValue());
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Inscrieri where idParticipant=? and idProba=?")) {
            preStmt.setInt(1, integerIntegerPair.getKey());
            preStmt.setInt(2, integerIntegerPair.getValue());
            int result = preStmt.executeUpdate();
            if (result == 0)
                throw new RepositoryException("Error: Nu s-a putut sterge inscrierea!");
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Pair<Integer, Integer> integerIntegerPair, Inscriere entity) {
        logger.traceEntry("updating inscriere with {}", integerIntegerPair);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("update Inscrieri " +
                "set data_op=?, operator=?" +
                "where idParticipant=? and idProba=?")) {
            preStmt.setInt(3, integerIntegerPair.getKey());
            preStmt.setInt(4, integerIntegerPair.getValue());
            Date date = Date.valueOf(entity.getData());
            preStmt.setDate(1, date);
            preStmt.setString(2, entity.getUsernameOperator());
            int result = preStmt.executeUpdate();
            if (result == 0)
                throw new RepositoryException("Error: Nu s-a putut actualiza inscrierea!");
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public Inscriere findOne(Pair<Integer, Integer> integerIntegerPair) {
        logger.traceEntry("finding inscriere with id {} ", integerIntegerPair);
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Inscrieri where idParticipant=? and idProba=?")) {
            preStmt.setInt(1, integerIntegerPair.getKey());
            preStmt.setInt(2, integerIntegerPair.getValue());
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    Integer idParticipant = result.getInt("idParticipant");
                    Integer idProba = result.getInt("idProba");
                    Date data = result.getDate("data_op");
                    String operator = result.getString("operator");
                    Inscriere inscriere =
                            new Inscriere(idParticipant, idProba, data.toLocalDate(), operator);
                    logger.traceExit(inscriere);
                    return inscriere;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit("No inscriere found with id {}", integerIntegerPair);

        return null;
    }

    @Override
    public Iterable<Inscriere> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        List<Inscriere> inscrieri = new ArrayList<>();

        try (PreparedStatement preStmt = con.prepareStatement("select * from Inscrieri")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer idParticipant = result.getInt("idParticipant");
                    Integer idProba = result.getInt("idProba");
                    Date data = result.getDate("data_op");
                    String operator = result.getString("operator");
                    Inscriere inscriere =
                            new Inscriere(idParticipant, idProba, data.toLocalDate(), operator);
                    inscrieri.add(inscriere);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(inscrieri);
        return inscrieri;
    }

    public Iterable<Inscriere> findProbeDupaParticipant(Integer idP) {
        logger.traceEntry("finding inscrieri with idParticipant {} ", idP);
        Connection con = dbUtils.getConnection();
        List<Inscriere> inscrieri = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Inscrieri where idParticipant=?")) {
            preStmt.setInt(1, idP);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Date data = result.getDate("data_op");
                    String operator = result.getString("operator");
                    Integer idProba = result.getInt("idProba");
                    Inscriere inscriere =
                            new Inscriere(idP, idProba, data.toLocalDate(), operator);
                    inscrieri.add(inscriere);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit(inscrieri);
        return inscrieri;
    }

    @Override
    public Iterable<Participant> cautaParticipantiDupaCategorie(String categorie) {
        logger.traceEntry("finding participants with categorie {} ", categorie);
        Connection con = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select P.id, P.nume, P.varsta from Participanti P inner join Inscrieri I on P.id==I.idParticipant inner join Probe Pr on Pr.id==I.idProba where Pr.categorie=?")) {
            preStmt.setString(1, categorie);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer varsta = result.getInt("varsta");
                    Participant participant = new Participant(id, nume, varsta);
                    participants.add(participant);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit(participants);
        return participants;
    }

    @Override
    public Iterable<Participant> cautaParticipantiDupaProba(String proba) {
        logger.traceEntry("finding participants with categorie {} ", proba);
        Connection con = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select P.id, P.nume, P.varsta from Participanti P inner join Inscrieri I on P.id==I.idParticipant inner join Probe Pr on Pr.id==I.idProba where Pr.denumire=?")) {
            preStmt.setString(1, proba);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer varsta = result.getInt("varsta");
                    Participant participant = new Participant(id, nume, varsta);
                    participants.add(participant);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit(participants);
        return participants;
    }

    @Override
    public Iterable<Participant> cautaParticipantDupaProbaCategorie(String proba, String categorie) {
        logger.traceEntry("finding participants with categorie {} ", categorie);
        Connection con = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select P.id, P.nume, P.varsta from Participanti P inner join Inscrieri I on P.id==I.idParticipant inner join Probe Pr on Pr.id==I.idProba where Pr.categorie=? and Pr.denumire=?")) {
            preStmt.setString(2, proba);
            preStmt.setString(1, categorie);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String nume = result.getString("nume");
                    Integer varsta = result.getInt("varsta");
                    Participant participant = new Participant(id, nume, varsta);
                    participants.add(participant);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit(participants);
        return participants;
    }

    @Override
    public int nrParticipantiProba(Proba proba) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Inscrieri I inner join Probe P on P.id=I.idProba where P.id=?")) {
            preStmt.setInt(1, proba.getID());
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        return 0;
    }

    @Override
    public int nrParticipantiCategorie(String categorie) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) as [SIZE] from Inscrieri I inner join Probe P on P.id=I.idProba where P.categorie=?")) {
            preStmt.setString(1, categorie);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        logger.traceEntry("deleting all");
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Inscrieri")) {
            int result = preStmt.executeUpdate();
            if (result == 0)
                throw new RepositoryException("Error: Nu s-au putut sterge inregistrarile!");
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }
}

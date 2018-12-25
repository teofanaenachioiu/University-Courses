import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Repository<Id,E extends hasId<Id>> {
    protected String idColumnName = "id";
    private DatabaseHandler databaseHandler;
    protected String tableName;
    protected static PreparedStatement preparedStatement = null;
    protected String getAllQuery;

    public Repository(DatabaseHandler databaseHandler, String tableName) {
        this.databaseHandler = databaseHandler;
        this.tableName = tableName;
        this.getAllQuery = "SELECT * FROM " + tableName;
    }

    public void add(E entity) {
        try {
            createAddStatement();
            populateStatementValues(entity);
            preparedStatement.execute();
        } catch (SQLException ex) {
            if (!ex.getMessage().contains("duplicate key value"))
                System.err.println("Unable to execute query.\n" + ex.getMessage());
            //throw new RepositoryException(ex.getMessage());
        }
    }

    public Optional<E> find(Id id) {
        try {
            String query = "SELECT *" +
                    " FROM " + tableName +
                    " WHERE id = ? " +
                    " FETCH FIRST ROW ONLY";

            preparedStatement = DatabaseHandler.getConnection().prepareStatement(query);

            if (id instanceof Integer)
                preparedStatement.setInt(1, ((Integer) id));
            else if (id instanceof String)
                preparedStatement.setString(1, ((String) id));

            ResultSet result = preparedStatement.executeQuery();

            Optional<E> returned = Optional.empty();
            if (result.next())
                returned = createEntity(result);

            return returned;
        } catch (SQLException ex) {
            System.err.println("Unable to execute query.\n" + ex.getMessage());
            return Optional.empty();
        }
    }

    public long size() {
        try {
            ResultSet resultSet = databaseHandler.execQuery("SELECT COUNT(*) FROM " + tableName);
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            System.out.println("Eroare la lungimea tabelului: " + e.getMessage());
            return 0;
        }
    }

    public E remove(Id id) {
        try {
            String queryGet = "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";
            String query = "DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?";

            preparedStatement = DatabaseHandler.getConnection().prepareStatement(queryGet);

            if (id instanceof Integer)
                preparedStatement.setInt(1, ((Integer) id));
            else if (id instanceof String)
                preparedStatement.setString(1, ((String) id));

            ResultSet result = preparedStatement.executeQuery();

            E returned = null;
            if (result.next()) {
                Optional<E> optionalReturned = createEntity(result);
                if (optionalReturned.isPresent())
                    returned = optionalReturned.get();
            }

            preparedStatement = DatabaseHandler.getConnection().prepareStatement(query);

            if (id instanceof Integer)
                preparedStatement.setInt(1, ((Integer) id));
            else if (id instanceof String)
                preparedStatement.setString(1, ((String) id));

            preparedStatement.execute();

            return returned;
        } catch (SQLException ex) {
            System.err.println("Unable to execute query.\n" + ex.getMessage());
            return null;
        }
    }

    public void removeAll() {
        databaseHandler.execAction("DELETE FROM " + tableName);
    }

    public ArrayList<E> getAll() {
        ResultSet resultSet = databaseHandler.execQuery(getAllQuery);
        ArrayList<E> elements = new ArrayList<>();

        if (resultSet == null)
            return null;

        try {
            while (resultSet.next()) {
                Optional<E> entity = createEntity(resultSet);
                entity.ifPresent(elements::add);
            }
        } catch (SQLException e) {
            System.out.println("Eroare la getAll: " + e.getMessage());
            return new ArrayList<>();
        }

        if (elements.isEmpty())
            return null;

        return elements;
    }

    public E update(E entity) {
        try {

            String queryGet = "SELECT * FROM " + tableName + " WHERE " + idColumnName + " = ?";

            preparedStatement = DatabaseHandler.getConnection().prepareStatement(queryGet);

            if (entity.getId() instanceof Integer)
                preparedStatement.setInt(1, ((Integer) entity.getId()));
            else if (entity.getId() instanceof String)
                preparedStatement.setString(1, ((String) entity.getId()));

            ResultSet result = preparedStatement.executeQuery();

            E returned = null;
            if (result.next()) {
                Optional<E> optionalReturned = createEntity(result);
                if (optionalReturned.isPresent())
                    returned = optionalReturned.get();
            }

            createEditStatement(entity.getId());
            populateStatementValues(entity);
            preparedStatement.execute();

            return returned;
        } catch (SQLException ex) {
            System.err.println("Unable to execute query.\n" + ex.getMessage());
            return null;
        }
    }

    public void importDataFromFile(String filename, String separator) {
        Path path = Paths.get(filename);
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(line -> {
                String[] fields = line.split(separator);
                E entity = createEntityFromFile(fields);
                add(entity);

            });
        } catch (FileNotFoundException e) {
            System.out.println("Nu exista fisierul " + filename + ".");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Input-Output error: " + e.getMessage());
        }
    }


    protected abstract E createEntityFromFile(String[] fields);

    protected abstract Optional<E> createEntity(ResultSet resultSet);

    protected abstract void createAddStatement() throws SQLException;

    protected abstract void createEditStatement(Id id) throws SQLException;

    protected abstract void populateStatementValues(E entity) throws SQLException;
}
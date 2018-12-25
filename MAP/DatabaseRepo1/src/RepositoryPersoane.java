import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RepositoryPersoane extends Repository<Integer,Persoana>{
    public RepositoryPersoane(DatabaseHandler databaseHandler) {
        super(databaseHandler, "Persoane");
        getAllQuery += " ORDER BY nume";
    }

    @Override
    protected Persoana createEntityFromFile(String[] fields) {
        int id = Integer.parseInt(fields[0]);
        String nume = fields[1];

        return new Persoana(id, nume);
    }

    @Override
    protected Optional<Persoana> createEntity(ResultSet resultSet) {
        Optional<Persoana> returned = Optional.empty();

        try {
            int id = resultSet.getInt("id");
            String nume = resultSet.getString("nume");

            Persoana student = new Persoana(id, nume);

            return Optional.of(student);
        } catch (SQLException e) {
            System.out.println("Eroare la create entity din result set : " + e.getMessage());
        }

        return returned;
    }

    @Override
    protected void createAddStatement() throws SQLException {
        String query = "INSERT INTO " + tableName +
                " (id, nume) values (?, ?)";
        preparedStatement = DatabaseHandler.getConnection().prepareStatement(query);
    }

    @Override
    protected void createEditStatement(Integer id) throws SQLException {
        String query = "UPDATE " + tableName + " SET" +
                " id = ?, nume = ? " +
                " WHERE id = ?";
        preparedStatement = DatabaseHandler.getConnection().prepareStatement(query);
        preparedStatement.setInt(7, id);
    }

    @Override
    protected void populateStatementValues(Persoana entity) throws SQLException {
        preparedStatement.setInt(1, entity.getId());
        preparedStatement.setString(2, entity.getNume());
    }
}

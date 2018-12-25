import javax.swing.*;
import java.sql.*;

public final class DatabaseHandler {
    private static final String DB_URL = "jdbc:derby:data/PersoaneDB;create=true";
    private static Connection connection = null;
    private static Statement statement = null;

    public DatabaseHandler() {
        createConnection();
        createPersoaneTable();
    }

    public static Connection getConnection() {
        return connection;
    }

    private void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").getConstructor().newInstance();
            connection = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Can't load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Nu sa putut conecta!!!");
            System.exit(0);

        }
    }

    private void createPersoaneTable() {
        String tableName = "Persoane";
        try {
            statement = connection.createStatement();

            DatabaseMetaData metadata = connection.getMetaData();
            ResultSet tables = metadata.getTables(null, null, tableName.toUpperCase(), null);

            if (! tables.next()) {
                statement.execute("CREATE TABLE " + tableName + "(" +
                        " id INTEGER primary key," +
                        " nume VARCHAR(200))");
            }
        } catch (SQLException e) {
            System.err.println(">>>>> "+e.getMessage() + " --- setupDatabase");
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(">>>>> Exception at execQuery:dataHandler : " + ex.getMessage());
            return null;
        }
        return result;
    }

    public boolean execAction(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (SQLException ex) {
            System.out.println(">>>>> Exception at execQuery:dataHandler : " + ex.getMessage());
            return false;
        }
    }
}

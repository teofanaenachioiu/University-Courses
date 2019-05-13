package repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private Properties jdbcProps;
    private static final Logger logger = LogManager.getLogger();

    public JdbcUtils() {
        Properties pro = new Properties();
        pro.setProperty("mtasks.jdbc.driver", "org.sqlite.JDBC");
        pro.setProperty("mtasks.jdbc.url", "jdbc:sqlite:D:/University-Courses/MPP/Laborator1/Persistence/src/main/resources/concurs.db");
        logger.info("Initializing ParticipantRepository with properties: {} ", pro);
        jdbcProps = pro;
    }

    private Connection instance = null;

    private Connection getNewConnection() {
        logger.traceEntry();
        String driver = jdbcProps.getProperty("mtasks.jdbc.driver");
        String url = jdbcProps.getProperty("mtasks.jdbc.url");
        logger.info("trying to connect to database ... {}", url);

        Connection con = null;
        try {
            Class.forName(driver);
            logger.info("Loaded driver ...{}", driver);
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Error loading driver " + e);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection " + e);
        }
        return con;
    }

    public Connection getConnection() {
        logger.traceEntry();
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
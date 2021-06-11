package database;

import repository.RepositoryException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final DatabaseProperties databaseProperties = new DatabaseProperties();

    private static final String DB_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    public static Connection getConnection() {
        try {
            Class.forName(DB_DRIVER);

            return DriverManager.getConnection(
                    databaseProperties.getUrl(),
                    databaseProperties.getUser(),
                    databaseProperties.getPassword()
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new RepositoryException(e);
        }
    }
}

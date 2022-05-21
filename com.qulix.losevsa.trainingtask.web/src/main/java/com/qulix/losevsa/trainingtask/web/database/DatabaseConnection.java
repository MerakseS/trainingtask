package com.qulix.losevsa.trainingtask.web.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The class for creating database connection.
 */
public class DatabaseConnection {

    private static final DatabaseProperties DATABASE_PROPERTIES = new DatabaseProperties();
    private static final String DB_DRIVER = "org.hsqldb.jdbc.JDBCDriver";

    /**
     * Gets database connection. Connection parameters are taken from {@link DatabaseProperties}.
     *
     * @return database connection
     */
    public static Connection getConnection() {
        try {
            Class.forName(DB_DRIVER);

            return DriverManager.getConnection(
                DATABASE_PROPERTIES.getUrl(),
                DATABASE_PROPERTIES.getUser(),
                DATABASE_PROPERTIES.getPassword()
            );
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new NoConnectionException("Can't get database connection", e);
        }
    }
}

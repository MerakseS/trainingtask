package com.qulix.losevsa.trainingtask.web.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static java.lang.String.format;

import org.apache.log4j.Logger;

/**
 * The class for creating com.qulix.losevsa.trainingtask.web.database connection/
 */
public class DatabaseConnection {

    private static final DatabaseProperties DATABASE_PROPERTIES = new DatabaseProperties();

    private static final String DB_DRIVER = "org.hsqldb.jdbc.JDBCDriver";
    private static final Logger LOG = Logger.getLogger(DatabaseConnection.class);

    /**
     * Gets connection for com.qulix.losevsa.trainingtask.web.database. Connection parameters are taken from {@link DatabaseProperties}
     *
     * @return the connection
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
            LOG.error(format("Can't get connection cause: %s", e.getMessage()));
            throw new NoConnectionException(e);
        }
    }
}

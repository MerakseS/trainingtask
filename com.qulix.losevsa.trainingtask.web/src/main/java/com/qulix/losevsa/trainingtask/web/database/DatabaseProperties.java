package com.qulix.losevsa.trainingtask.web.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static java.lang.String.format;

import org.apache.log4j.Logger;

/**
 * Represents database connection properties.
 * Sets all connection parameters according to db.properties file
 * If db.properties is missing, class uses default connection parameters.
 */
public class DatabaseProperties {

    private static final Logger LOG = Logger.getLogger(DatabaseProperties.class);

    private static final String PROPERTIES_PATH = "/db/db.properties";

    private static final String HSQLDB_HOST_VARIABLE = "db.host";
    private static final String HSQLDB_NAME_VARIABLE = "db.name";
    private static final String HSQLDB_USER_VARIABLE = "db.user";
    private static final String HSQLDB_PASSWORD_VARIABLE = "db.password";

    private static final String DEFAULT_DB_HOST = "localhost";
    private static final String DEFAULT_DB_NAME = "taskdb";
    private static final String DEFAULT_DB_USER = "SA";
    private static final String DEFAULT_DB_PASSWORD = "";

    private static final String HSQLDB_URL_FORMAT = "jdbc:hsqldb:hsql://%s/%s";

    private final Properties properties = new Properties();

    /**
     * Database URL address.
     */
    private final String url;

    /**
     * Database username.
     */
    private final String user;


    /**
     * Username's password.
     */
    private final String password;

    /**
     * Instantiates a new Database properties.
     */
    public DatabaseProperties() {
        String dbHost;
        String dbName;
        String dbUser;
        String dbPassword;

        try (InputStream inputStream = DatabaseProperties.class.getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(inputStream);

            dbHost = getPropVariable(HSQLDB_HOST_VARIABLE, DEFAULT_DB_HOST);
            dbName = getPropVariable(HSQLDB_NAME_VARIABLE, DEFAULT_DB_NAME);
            dbUser = getPropVariable(HSQLDB_USER_VARIABLE, DEFAULT_DB_USER);
            dbPassword = getPropVariable(HSQLDB_PASSWORD_VARIABLE, DEFAULT_DB_PASSWORD);
        }
        catch (IOException e) {
            LOG.warn("Can't load database properties from file cause:", e);

            dbHost = DEFAULT_DB_HOST;
            dbName = DEFAULT_DB_NAME;
            dbUser = DEFAULT_DB_USER;
            dbPassword = DEFAULT_DB_PASSWORD;
        }

        url = format(HSQLDB_URL_FORMAT, dbHost, dbName);
        user = dbUser;
        password = dbPassword;
    }

    private String getPropVariable(String variableName, String defaultVariable) {
        String variable = properties.getProperty(variableName);

        if (variable == null || variable.isBlank()) {
            variable = defaultVariable;
        }

        return variable;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}

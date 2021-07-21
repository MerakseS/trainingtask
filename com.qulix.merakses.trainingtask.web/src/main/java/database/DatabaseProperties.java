package database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Represents database connection properties.
 * Sets all connection parameters according to db.properties file
 * If db.properties is missing, class uses default connection parameters.
 */
public class DatabaseProperties {

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

    private final String url;
    private final String user;
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
        } catch (IOException e) {
            dbHost = DEFAULT_DB_HOST;
            dbName = DEFAULT_DB_NAME;
            dbUser = DEFAULT_DB_USER;
            dbPassword = DEFAULT_DB_PASSWORD;

            e.printStackTrace();
        }

        url = String.format(HSQLDB_URL_FORMAT, dbHost, dbName);
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

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}

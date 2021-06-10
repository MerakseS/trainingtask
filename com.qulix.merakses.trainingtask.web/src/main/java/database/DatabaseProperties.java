package database;

public class DatabaseProperties {

    private static final String HSQLDB_HOST_VARIABLE = "HSQLDB_PATH";
    private static final String HSQLDB_NAME_VARIABLE = "HSQLDB_NAME";
    private static final String HSQLDB_USER_VARIABLE = "HSQLDB_USER";
    private static final String HSQLDB_PASSWORD_VARIABLE = "HSQLDB_PASSWORD";

    private static final String DEFAULT_DB_HOST = "localhost";
    private static final String DEFAULT_DB_NAME = "taskdb";
    private static final String DEFAULT_DB_USER = "SA";
    private static final String DEFAULT_DB_PASSWORD = "";

    private static final String HSQLDB_URL_FORMAT = "jdbc:hsqldb:hsql://%s/%s";

    private final String url;
    private final String user;
    private final String password;

    public DatabaseProperties() {
        String dbHost = getEnvVariable(HSQLDB_HOST_VARIABLE, DEFAULT_DB_HOST);
        String dbName = getEnvVariable(HSQLDB_NAME_VARIABLE, DEFAULT_DB_NAME);
        url = String.format(HSQLDB_URL_FORMAT, dbHost, dbName);

        user = getEnvVariable(HSQLDB_USER_VARIABLE, DEFAULT_DB_USER);
        password = getEnvVariable(HSQLDB_PASSWORD_VARIABLE, DEFAULT_DB_PASSWORD);
    }

    private static String getEnvVariable(String variableName, String defaultVariable) {
        String variable = System.getenv(variableName);

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

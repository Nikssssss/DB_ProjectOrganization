package modules.login;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoginInteractor {
    private Connection connection;

    public LoginInteractor() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (connection != null) {
                    this.dropAllObjectsWithKind("tables");
                    this.dropAllObjectsWithKind("sequences");
                    this.dropAllObjectsWithKind("autoincrement_triggers");
                    connection.close();
                }
            } catch (SQLException ignored) {}
        }));
    }

    public void connect(String ip, String port, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        DriverManager.setLoginTimeout(2);
        Locale.setDefault(Locale.ENGLISH);
        String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":XE";
        connection = DriverManager.getConnection(url, user, password);
    }

    public void initializeDatabase() throws SQLException{
        this.createTables();
        this.createSequences();
        this.createAutoincrementTriggers();
        this.initializeTables();
    }

    public Connection getConnection() {
        return connection;
    }

    //MARK: private methods

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("create_tables.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
    }

    private void createSequences() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("create_sequences.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
    }

    private void createAutoincrementTriggers() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("create_autoincrement_triggers.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split("end;");
        for (String query: splitQueries) {
            statement.addBatch(query + " end;");
        }
        statement.executeBatch();
    }

    private void initializeTables() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("init_inserts.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
    }

    private void dropAllObjectsWithKind(String objectKind) throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("drop_" + objectKind + ".sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
    }
}

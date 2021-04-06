package modules.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainInteractor {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void initializeDatabase() throws SQLException {
        this.createTables();
        this.createSequences();
        this.createAutoincrementTriggers();
        this.initializeTables();
    }

    public void clearDatabase() throws SQLException {
        this.dropAllObjectsWithKind("tables");
        this.dropAllObjectsWithKind("sequences");
    }

    //MARK: private methods

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getResourceAsStream("/create_tables.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
        statement.close();
        connection.commit();
    }

    private void createSequences() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getResourceAsStream("/create_sequences.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
        statement.close();
        connection.commit();
    }

    private void createAutoincrementTriggers() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getResourceAsStream("/create_autoincrement_triggers.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split("end;");
        for (String query: splitQueries) {
            statement.addBatch(query + " end;");
        }
        statement.executeBatch();
        statement.close();
        connection.commit();
    }

    private void initializeTables() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getResourceAsStream("/init_inserts.sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
        statement.close();
        connection.commit();
    }

    private void dropAllObjectsWithKind(String objectKind) throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = this.getClass().getResourceAsStream("/drop_" + objectKind + ".sql");
        String createQuery = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining());
        String[] splitQueries = createQuery.split(";");
        for (String query: splitQueries) {
            statement.addBatch(query);
        }
        statement.executeBatch();
        statement.close();
        connection.commit();
    }
}

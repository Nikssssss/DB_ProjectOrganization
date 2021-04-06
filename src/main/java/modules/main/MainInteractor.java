package modules.main;

import services.QueriesExecutor;

import java.sql.SQLException;

public class MainInteractor {

    public void initializeDatabase() throws SQLException {
        QueriesExecutor.createTables();
        QueriesExecutor.createSequences();
        QueriesExecutor.createAutoincrementTriggers();
        QueriesExecutor.initializeTables();
    }

    public void clearDatabase() throws SQLException {
        QueriesExecutor.dropAllObjects();
    }

    public void checkDatabaseCreation() throws SQLException {
        QueriesExecutor.getAllDepartments();
    }

}

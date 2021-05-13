package modules.roles;

import services.QueriesExecutor;

import java.sql.SQLException;

public class RolesInteractor {
    public void createDatabase() throws SQLException {
        QueriesExecutor.createTables();
        QueriesExecutor.createSequences();
        QueriesExecutor.createAutoincrementTriggers();
        QueriesExecutor.createRoles();
    }

    public void removeDatabase() throws SQLException {
        QueriesExecutor.deleteAllUsers();
        QueriesExecutor.dropAllObjects();
    }

    public void initializeDatabase() throws SQLException {
        QueriesExecutor.initializeTables();
    }

    public void clearDatabase() throws SQLException {
        QueriesExecutor.deleteAllUsers();
        QueriesExecutor.dropAllObjects();
        QueriesExecutor.createTables();
        QueriesExecutor.createSequences();
        QueriesExecutor.createAutoincrementTriggers();
    }

    public void checkDatabaseCreation() throws SQLException {
        QueriesExecutor.getAllDepartments();
    }
}

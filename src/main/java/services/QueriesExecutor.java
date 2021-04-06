package services;

import common.DatabaseConnection;
import modules.tables.enums.TableType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueriesExecutor {
    private static Connection connection;

    private QueriesExecutor() {}

    public static void setConnection() {
        connection = DatabaseConnection.getConnection();
    }

    public static void createTables() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = QueriesExecutor.class.getResourceAsStream("/create_tables.sql");
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

    public static void createSequences() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = QueriesExecutor.class.getResourceAsStream("/create_sequences.sql");
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

    public static void createAutoincrementTriggers() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = QueriesExecutor.class.getResourceAsStream("/create_autoincrement_triggers.sql");
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

    public static void initializeTables() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = QueriesExecutor.class.getResourceAsStream("/init_inserts.sql");
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

    public static void dropAllObjects() throws SQLException {
        Statement statement = connection.createStatement();
        InputStream inputStream = QueriesExecutor.class.getResourceAsStream("/drop_objects.sql");
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

    public static ResultSet getResultSetFromTable(TableType tableType, String tableColumns) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String query = "select " + tableColumns + " from " + tableType.toString();
        return statement.executeQuery(query);
    }

    public static ArrayList<String> getAllProfessionNames() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from professions";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professions = new ArrayList<>();
        while (resultSet.next()) {
            professions.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return professions;
    }

    public static ArrayList<String> getAllManagers() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from employees";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professions = new ArrayList<>();
        professions.add("-");
        while (resultSet.next()) {
            professions.add(resultSet.getString(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3));
        }
        resultSet.close();
        statement.close();
        return professions;
    }

    public static ArrayList<String> getAllDepartments() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_name from departments";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professions = new ArrayList<>();
        while (resultSet.next()) {
            professions.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return professions;
    }

    public static String getAllColumnNamesOfTable(TableType tableType) {
        switch (tableType) {
            case EMPLOYEES: return "employee_id, first_name, last_name, hire_date, profession_id, salary, age";
            case DEPARTMENTS: return "department_id, department_name, manager_id";
            case PROFESSIONS: return "profession_id, profession_name, management_ability, department_id";
            default: return "";
        }
    }

    public static String getProfessionNameBy(String professionId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from professions where profession_id = " + professionId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getManagerInfoBy(String managerId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from employees where employee_id = " + managerId;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String result = resultSet.getString(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3);
            resultSet.close();
            statement.close();
            return result;
        } else {
            return "-";
        }
    }

    public static String getDepartmentNameBy(String departmentId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_name from departments where department_id = " + departmentId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getProfessionIdBy(String professionName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_id from professions where profession_name = '" + professionName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getDepartmentIdBy(String departmentName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_id from departments where department_name = '" + departmentName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static ResultSet executeBusinessQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

}

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
import java.util.HashMap;
import java.util.Locale;
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

    public static ArrayList<String> getUserRole() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select granted_role from USER_ROLE_PRIVS";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> roles = new ArrayList<>();
        while (resultSet.next()) {
            roles.add(resultSet.getString(1).toLowerCase(Locale.ROOT));
        }
        return roles;
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

    public static void deleteAllUsers() throws SQLException {
        System.out.println("deleteAllUsers");
        Statement statement = connection.createStatement();
        String query = "select login from employees";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> userLogins = new ArrayList<>();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            if (resultSet.getString(1) != null) {
                userLogins.add(resultSet.getString(1));
            }
        }
        for (String userLogin: userLogins) {
            statement.execute("drop user " + userLogin + " cascade");
        }
        resultSet.close();
        statement.close();
    }

    public static ResultSet getResultSetFromTable(TableType tableType, String tableColumns) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String query = "select " + tableColumns + " from " + "ngusev1." + tableType.toString();
        return statement.executeQuery(query);
    }

    public static ArrayList<String> getAllProfessionNames() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from ngusev1.professions";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professions = new ArrayList<>();
        while (resultSet.next()) {
            professions.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return professions;
    }

    public static ArrayList<String> getAllProjects() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select project_name from ngusev1.projects";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> projectIDs = new ArrayList<>();
        while (resultSet.next()) {
            projectIDs.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return projectIDs;
    }

    public static ArrayList<String> getAllContracts() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select contract_name from ngusev1.contracts";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> contractIDs = new ArrayList<>();
        while (resultSet.next()) {
            contractIDs.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return contractIDs;
    }

    public static ArrayList<String> getAllSubcontracts() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select subcontract_name from ngusev1.subcontracts";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> subcontractIDs = new ArrayList<>();
        while (resultSet.next()) {
            subcontractIDs.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return subcontractIDs;
    }

    public static ArrayList<String> getAllManagers() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from ngusev1.employees";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> managers = new ArrayList<>();
        managers.add("-");
        while (resultSet.next()) {
            managers.add(resultSet.getString(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3));
        }
        resultSet.close();
        statement.close();
        return managers;
    }

    public static ArrayList<String> getAllEmployees() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from ngusev1.employees";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(resultSet.getString(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3));
        }
        resultSet.close();
        statement.close();
        return employees;
    }

    public static ArrayList<String> getAllUnusedEquipment() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select equipment_id, equipment_name from ngusev1.equipment" +
                " where equipment_id not in " +
                "(select equipment_id from ngusev1.equipment_projects group by equipment_id)";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> equipment = new ArrayList<>();
        while (resultSet.next()) {
            equipment.add(resultSet.getString(1) + ": " + resultSet.getString(2));
        }
        resultSet.close();
        statement.close();
        return equipment;
    }

    public static ArrayList<String> getAllProjectAndContractManagers() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from ngusev1.employees " +
                "inner join ngusev1.professions using(profession_id)" +
                " where management_ability = 1";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> managers = new ArrayList<>();
        while (resultSet.next()) {
            managers.add(resultSet.getString(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3));
        }
        resultSet.close();
        statement.close();
        return managers;
    }

    public static ArrayList<String> getAllDepartments() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_name from ngusev1.departments";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professions = new ArrayList<>();
        while (resultSet.next()) {
            professions.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return professions;
    }

    public static ArrayList<String> getAllEquipmentTypes() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select equipment_type_name from ngusev1.EquipmentType";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> equipmentTypes = new ArrayList<>();
        while (resultSet.next()) {
            equipmentTypes.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return equipmentTypes;
    }

    public static String getAllColumnNamesOfTable(TableType tableType) {
        switch (tableType) {
            case EMPLOYEES: return "employee_id, first_name, last_name, hire_date, profession_id, salary, age";
            case DEPARTMENTS: return "department_id, department_name, manager_id";
            case PROFESSIONS: return "profession_id, profession_name, management_ability, department_id";
            case EQUIPMENT: return "equipment_id, equipment_name, equipment_type_id, department_id";
            case PROJECTS: return "project_id, project_name, project_manager, project_cost, start_date, finish_date";
            case CONTRACTS: return "contract_id, contract_name, contract_manager, start_date, finish_date";
            case SUBCONTRACTS: return "subcontract_id, subcontract_name, subcontractor_name, start_date, finish_date";
            case EQUIPMENTTYPE: return "equipment_type_id, equipment_type_name";
            case PROJECTS_EMPLOYEES: return "employee_id, project_id";
            case EQUIPMENT_PROJECTS: return "equipment_id, project_id";
            case TECHNICS: return "employee_id, equipment_type_id";
            case ENGINEERS: return "employee_id, projecting_program";
            case CONSTRUCTORS: return "employee_id, certificates_number";
            case ACCOUNTANTS: return "employee_id, accounting_program";
            case MANAGERS: return "employee_id, interviews_number";
            default: return "";
        }
    }

    public static String getProfessionNameBy(String professionId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from ngusev1.professions where profession_id = " + professionId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getManagerInfoBy(String managerId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from ngusev1.employees where employee_id = " + managerId;
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
        String query = "select department_name from ngusev1.departments where department_id = " + departmentId;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String result = resultSet.getString(1);
            resultSet.close();
            statement.close();
            return result;
        } else {
            return "";
        }
    }

    public static String getEquipmentNameBy(String equipmentId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select equipment_name from ngusev1.equipment where equipment_id = " + equipmentId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getEquipmentIdBy(String equipmentName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select equipment_id from ngusev1.equipment where equipment_name = '" + equipmentName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getEquipmentTypeNameBy(String equipmentId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select equipment_type_name from ngusev1.equipmentType where equipment_type_id = " + equipmentId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getProfessionIdBy(String professionName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_id from ngusev1.professions where profession_name = '" + professionName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getDepartmentIdBy(String departmentName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_id from ngusev1.departments where department_name = '" + departmentName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getEquipmentTypeIdBy(String equipmentType) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select equipment_type_id from ngusev1.equipmentType where equipment_type_name = '" + equipmentType + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getProjectNameBy(String projectId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select project_name from ngusev1.projects where project_id = " + projectId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static void createRoles() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "create role director_ng";
        statement.execute(query);
        provideGrant(statement, "select", "equipment", "director_ng");
        provideGrant(statement, "select", "equipmentType", "director_ng");
        provideGrant(statement, "select", "departments", "director_ng");
        provideGrant(statement, "select", "employees", "director_ng");
        provideGrant(statement, "select", "professions", "director_ng");
        provideGrant(statement, "select", "projects", "director_ng");
        provideGrant(statement, "select", "contracts", "director_ng");
        provideGrant(statement, "select", "subcontracts", "director_ng");
        provideGrant(statement, "select", "projects_employees", "director_ng");
        provideGrant(statement, "select", "equipment_projects", "director_ng");
        provideGrant(statement, "select", "subcontracts_projects", "director_ng");
        provideGrant(statement, "select", "projects_contracts", "director_ng");
        provideGrant(statement, "insert", "equipment", "director_ng");
        provideGrant(statement, "insert", "equipmentType", "director_ng");
        provideGrant(statement, "insert", "departments", "director_ng");
        provideGrant(statement, "update", "equipment", "director_ng");
        provideGrant(statement, "update", "equipmentType", "director_ng");
        provideGrant(statement, "update", "departments", "director_ng");
        provideGrant(statement, "delete", "equipment", "director_ng");
        provideGrant(statement, "delete", "equipmentType", "director_ng");
        provideGrant(statement, "delete", "departments", "director_ng");

        query = "create role manager_ng";
        statement.execute(query);
        provideGrant(statement, "select", "projects", "manager_ng");
        provideGrant(statement, "select", "contracts", "manager_ng");
        provideGrant(statement, "select", "subcontracts", "manager_ng");
        provideGrant(statement, "select", "projects_employees", "manager_ng");
        provideGrant(statement, "select", "equipment_projects", "manager_ng");
        provideGrant(statement, "select", "subcontracts_projects", "manager_ng");
        provideGrant(statement, "select", "projects_contracts", "manager_ng");
        provideGrant(statement, "select", "employees", "manager_ng");
        provideGrant(statement, "select", "equipment", "manager_ng");
        provideGrant(statement, "select", "professions", "manager_ng");
        provideGrant(statement, "update", "projects", "manager_ng");
        provideGrant(statement, "update", "contracts", "manager_ng");
        provideGrant(statement, "update", "subcontracts", "manager_ng");
        provideGrant(statement, "update", "projects_employees", "manager_ng");
        provideGrant(statement, "update", "equipment_projects", "manager_ng");
        provideGrant(statement, "insert", "projects", "manager_ng");
        provideGrant(statement, "insert", "contracts", "manager_ng");
        provideGrant(statement, "insert", "subcontracts", "manager_ng");
        provideGrant(statement, "insert", "projects_employees", "manager_ng");
        provideGrant(statement, "insert", "equipment_projects", "manager_ng");
        provideGrant(statement, "insert", "projects_contracts", "manager_ng");
        provideGrant(statement, "insert", "subcontracts_projects", "manager_ng");
        provideGrant(statement, "delete", "projects", "manager_ng");
        provideGrant(statement, "delete", "contracts", "manager_ng");
        provideGrant(statement, "delete", "subcontracts", "manager_ng");
        provideGrant(statement, "delete", "projects_employees", "manager_ng");
        provideGrant(statement, "delete", "equipment_projects", "manager_ng");

        query = "create role hr_ng";
        statement.execute(query);
        provideGrant(statement, "select", "employees", "hr_ng");
        provideGrant(statement, "select", "technics", "hr_ng");
        provideGrant(statement, "select", "engineers", "hr_ng");
        provideGrant(statement, "select", "constructors", "hr_ng");
        provideGrant(statement, "select", "accountants", "hr_ng");
        provideGrant(statement, "select", "managers", "hr_ng");
        provideGrant(statement, "select", "professions", "hr_ng");
        provideGrant(statement, "select", "equipmentType", "hr_ng");
        provideGrant(statement, "select", "departments", "hr_ng");
        provideGrant(statement, "insert", "employees", "hr_ng");
        provideGrant(statement, "insert", "technics", "hr_ng");
        provideGrant(statement, "insert", "engineers", "hr_ng");
        provideGrant(statement, "insert", "constructors", "hr_ng");
        provideGrant(statement, "insert", "accountants", "hr_ng");
        provideGrant(statement, "insert", "managers", "hr_ng");
        provideGrant(statement, "insert", "professions", "hr_ng");
        provideGrant(statement, "update", "employees", "hr_ng");
        provideGrant(statement, "update", "technics", "hr_ng");
        provideGrant(statement, "update", "engineers", "hr_ng");
        provideGrant(statement, "update", "constructors", "hr_ng");
        provideGrant(statement, "update", "accountants", "hr_ng");
        provideGrant(statement, "update", "managers", "hr_ng");
        provideGrant(statement, "update", "professions", "hr_ng");
        provideGrant(statement, "delete", "employees", "hr_ng");
        provideGrant(statement, "delete", "technics", "hr_ng");
        provideGrant(statement, "delete", "engineers", "hr_ng");
        provideGrant(statement, "delete", "constructors", "hr_ng");
        provideGrant(statement, "delete", "accountants", "hr_ng");
        provideGrant(statement, "delete", "managers", "hr_ng");
        provideGrant(statement, "delete", "professions", "hr_ng");
        statement.close();
    }

    private static void provideGrant(Statement statement, String command, String table, String role) throws SQLException {
        String query = "grant " + command + " on ngusev1." + table + " to " + role;
        statement.execute(query);
    }

    public static ArrayList<String> getAllUnregisteredEmployees() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from employees " +
                "where login is null";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> employees = new ArrayList<>();
        while (resultSet.next()) {
            String readableEmployee = resultSet.getString(1) + ": " +
                    resultSet.getString(2) + " " + resultSet.getString(3);
            employees.add(readableEmployee);
        }
        return employees;
    }

    public static void registerEmployee(String employeeId, String login, String password) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from employees " +
                "inner join professions using(profession_id)" +
                " where employee_id = " + employeeId;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String profession = resultSet.getString(1);
            String role;
            switch (profession) {
                case "Инженер":
                case "Конструктор":
                case "Техник":
                    role = "manager_ng";
                    break;
                case "Бухгалтер":
                case "Менеджер":
                    role = "hr_ng";
                    break;
                default:
                    role = "director_ng";
                    break;
            }
            query = "create user " + login + " identified by " + password
                    + " default tablespace users temporary tablespace temp";
            statement.execute(query);
            query = "grant connect to " + login;
            statement.execute(query);
            query = "grant " + role + " to " + login;
            statement.execute(query);
            query = "update employees set login = '" + login + "', password = '" + password + "' where employee_id = " + employeeId;
            statement.execute(query);
        }
    }

    public static ResultSet executeBusinessQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static String getEmployeeProfession(String login, String password) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from ngusev1.employees " +
                "inner join ngusev1.professions using(profession_id) " +
                "where login = '" + login + "' and password = '" + password + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }
    }

    public static String getProjectIdBy(String projectName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select project_id from ngusev1.projects where project_name = '" + projectName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getContractIdBy(String contractName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select contract_id from ngusev1.contracts where contract_name = '" + contractName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    public static String getSubcontractIdBy(String subcontractName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select subcontract_id from ngusev1.subcontracts where subcontract_name = '" + subcontractName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }
}

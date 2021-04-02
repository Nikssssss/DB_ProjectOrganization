package modules.tables;

import modules.tables.entities.DepartmentData;
import modules.tables.entities.EmployeeData;
import modules.tables.entities.ProfessionData;
import modules.tables.enums.TableType;
import services.DataHandlerService;

import java.sql.*;
import java.util.ArrayList;

public class TablesInteractor {
    private Connection connection;
    private DataHandlerService dataHandlerService;
    private ResultSet currentTableResultSet;
    private ResultSetMetaData currentTableResultSetMetaData;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        this.dataHandlerService = new DataHandlerService(connection);
    }

    public ArrayList<ArrayList<String>> getDataFromTable(TableType tableType) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String allTableFields = "";
        switch (tableType) {
            case EMPLOYEES: {
                allTableFields = this.getAllEmployeesFields();
                break;
            }
            case DEPARTMENTS: {
                allTableFields = this.getAllDepartmentsFields();
                break;
            }
            case PROFESSIONS: {
                allTableFields = this.getAllProfessionsFields();
                break;
            }
        }
        String query = "select " + allTableFields + " from " + tableType.toString();
        this.currentTableResultSet = statement.executeQuery(query);
        this.currentTableResultSetMetaData = this.currentTableResultSet.getMetaData();

        int columnsCount = this.currentTableResultSetMetaData.getColumnCount();
        ArrayList<ArrayList<String>> resultData = new ArrayList<>();
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= columnsCount; i++) {
            columnNames.add(this.currentTableResultSetMetaData.getColumnName(i));
        }
        resultData.add(columnNames);

        while (this.currentTableResultSet.next()) {
            ArrayList<String> currentRow = new ArrayList<>();
            for (int i = 1; i <= columnsCount; i++) {
                currentRow.add(this.currentTableResultSet.getString(i));
            }
            resultData.add(currentRow);
        }

        resultData = this.dataHandlerService.getReadableDataFrom(resultData, tableType);

        return resultData;
    }

    public ArrayList<ArrayList<String>> getDataFromCurrentTable(TableType tableType) throws SQLException {
        int columnsCount = this.currentTableResultSetMetaData.getColumnCount();
        ArrayList<ArrayList<String>> resultData = new ArrayList<>();
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= columnsCount; i++) {
            columnNames.add(this.currentTableResultSetMetaData.getColumnName(i));
        }
        resultData.add(columnNames);

        this.currentTableResultSet.beforeFirst();
        while (this.currentTableResultSet.next()) {
            ArrayList<String> currentRow = new ArrayList<>();
            for (int i = 1; i <= columnsCount; i++) {
                currentRow.add(this.currentTableResultSet.getString(i));
            }
            resultData.add(currentRow);
        }

        resultData = this.dataHandlerService.getReadableDataFrom(resultData, tableType);

        return resultData;
    }

    public ArrayList<String> getAllProfessions() throws SQLException {
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

    public ArrayList<String> getAllManagers() throws SQLException {
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

    public ArrayList<String> getAllDepartments() throws SQLException {
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

    public ArrayList<String> getAllManagementAbilities() {
        ArrayList<String> managementAbilities = new ArrayList<>();
        managementAbilities.add("Да");
        managementAbilities.add("Нет");
        return managementAbilities;
    }

    public void updateRow(int row, ArrayList<String> rowData, TableType tableType) throws SQLException {
        switch (tableType) {
            case EMPLOYEES: {
                this.updateEmployeesRow(row, rowData);
                break;
            }
            case DEPARTMENTS: {
                this.updateDepartmentsRow(row, rowData);
                break;
            }
            case PROFESSIONS: {
                this.updateProfessionsRow(row, rowData);
                break;
            }
        }
    }

    public void deleteRow(int row) throws SQLException {
        this.currentTableResultSet.absolute(row);
        this.currentTableResultSet.deleteRow();
        this.connection.commit();
    }

    public ResultSet getCurrentTableResultSet() {
        return currentTableResultSet;
    }

    //MARK: private methods

    private String getAllEmployeesFields() {
        return "employee_id, first_name, last_name, hire_date, profession_id, salary, age";
    }

    private String getAllDepartmentsFields() {
        return "department_id, department_name, manager_id";
    }

    private String getAllProfessionsFields() {
        return "profession_id, profession_name, management_ability, department_id";
    }

    private void updateEmployeesRow(int row, ArrayList<String> rowData) throws SQLException {
        EmployeeData employeeData = this.dataHandlerService.getDatabaseRowDataFromEmployees(rowData);
        this.currentTableResultSet.absolute(row);
        this.currentTableResultSet.updateString(2, employeeData.getFirstName());
        this.currentTableResultSet.updateString(3, employeeData.getLastName());
        this.currentTableResultSet.updateDate(4, employeeData.getHireDate());
        this.currentTableResultSet.updateInt(5, employeeData.getProfessionId());
        this.currentTableResultSet.updateInt(6, employeeData.getSalary());
        this.currentTableResultSet.updateInt(7, employeeData.getAge());
        this.currentTableResultSet.updateRow();
        this.connection.commit();
    }

    private void updateDepartmentsRow(int row, ArrayList<String> rowData) throws SQLException {
        DepartmentData departmentData = this.dataHandlerService.getDatabaseRowDataFromDepartments(rowData);
        this.currentTableResultSet.absolute(row);
        this.currentTableResultSet.updateString(2, departmentData.getDepartmentName());
        this.currentTableResultSet.updateInt(3, departmentData.getManagerId());
        this.currentTableResultSet.updateRow();
        this.connection.commit();
    }

    private void updateProfessionsRow(int row, ArrayList<String> rowData) throws SQLException {
        ProfessionData professionData = this.dataHandlerService.getDatabaseRowDataFromProfessions(rowData);
        this.currentTableResultSet.absolute(row);
        this.currentTableResultSet.updateString(2, professionData.getProfessionName());
        this.currentTableResultSet.updateInt(3, professionData.getManagementAbility());
        this.currentTableResultSet.updateInt(4, professionData.getDepartmentId());
        this.currentTableResultSet.updateRow();
        this.connection.commit();
    }
}

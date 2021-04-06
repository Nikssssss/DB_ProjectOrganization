package modules.adding;

import modules.tables.entities.DepartmentData;
import modules.tables.entities.EmployeeData;
import modules.tables.entities.ProfessionData;
import modules.tables.enums.TableType;
import services.DataHandlerService;

import java.sql.*;
import java.util.ArrayList;

public class AddingInteractor {
    private Connection connection;
    private ResultSet resultSet;
    private TableType currentTableType;
    private DataHandlerService dataHandlerService;

    public void setConnection(Connection connection) {
        this.connection = connection;
        dataHandlerService = new DataHandlerService(connection);
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public void setCurrentTableType(TableType tableType) {
        this.currentTableType = tableType;
    }

    public TableType getCurrentTableType() {
        return currentTableType;
    }

    public ArrayList<String> getAllProfessionNames() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from professions";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professionNames = new ArrayList<>();
        while (resultSet.next()) {
            professionNames.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return professionNames;
    }

    public ArrayList<String> getAllManagersInfo() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from employees";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professionNames = new ArrayList<>();
        while (resultSet.next()) {
            professionNames.add(resultSet.getString(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3));
        }
        resultSet.close();
        statement.close();
        return professionNames;
    }

    public ArrayList<String> getAllDepartmentNames() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_name from departments";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professionNames = new ArrayList<>();
        while (resultSet.next()) {
            professionNames.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return professionNames;
    }

    public ArrayList<String> getAllManagementAbilities() {
        ArrayList<String> managementAbilities = new ArrayList<>();
        managementAbilities.add("Да");
        managementAbilities.add("Нет");
        return managementAbilities;
    }

    public void insertRow(ArrayList<String> insertingData) throws SQLException, IllegalArgumentException {
        resultSet.moveToInsertRow();
        switch (currentTableType) {
            case EMPLOYEES: {
                EmployeeData employeeData = dataHandlerService.getDatabaseRowDataFromEmployees(insertingData);
                resultSet.updateString(2, employeeData.getFirstName());
                resultSet.updateString(3, employeeData.getLastName());
                resultSet.updateDate(4, employeeData.getHireDate());
                resultSet.updateInt(5, employeeData.getProfessionId());
                resultSet.updateInt(6, employeeData.getSalary());
                resultSet.updateInt(7, employeeData.getAge());
                break;
            }
            case DEPARTMENTS: {
                DepartmentData departmentData = dataHandlerService.getDatabaseRowDataFromDepartments(insertingData);
                resultSet.updateString(2, departmentData.getDepartmentName());
                resultSet.updateInt(3, departmentData.getManagerId());
                break;
            }
            case PROFESSIONS: {
                ProfessionData professionData = dataHandlerService.getDatabaseRowDataFromProfessions(insertingData);
                resultSet.updateString(2, professionData.getProfessionName());
                resultSet.updateInt(3, professionData.getManagementAbility());
                resultSet.updateInt(4, professionData.getDepartmentId());
            }
        }
        resultSet.insertRow();
        connection.commit();
    }

    //MARK: private methods

}

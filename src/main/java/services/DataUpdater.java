package services;

import common.DatabaseConnection;
import common.entities.DepartmentData;
import common.entities.EmployeeData;
import common.entities.ProfessionData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataUpdater {
    private static Connection connection;

    private DataUpdater() {}

    public static void setConnection() {
        connection = DatabaseConnection.getConnection();
    }

    public static void updateEmployeesRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        EmployeeData employeeData = DataTransformer.getDatabaseEmployeesExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateString(2, employeeData.getFirstName());
        resultSet.updateString(3, employeeData.getLastName());
        resultSet.updateDate(4, employeeData.getHireDate());
        resultSet.updateInt(5, employeeData.getProfessionId());
        resultSet.updateInt(6, employeeData.getSalary());
        resultSet.updateInt(7, employeeData.getAge());
        resultSet.updateRow();
        connection.commit();
    }

    public static void updateDepartmentsRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        DepartmentData departmentData = DataTransformer.getDatabaseDepartmentsExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateString(2, departmentData.getDepartmentName());
        resultSet.updateObject(3, departmentData.getManagerId());
        resultSet.updateRow();
        connection.commit();
    }

    public static void updateProfessionsRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        ProfessionData professionData = DataTransformer.getDatabaseProfessionsExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateString(2, professionData.getProfessionName());
        resultSet.updateInt(3, professionData.getManagementAbility());
        resultSet.updateInt(4, professionData.getDepartmentId());
        resultSet.updateRow();
        connection.commit();
    }

    public static void insertEmployeesRow(ArrayList<String> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        EmployeeData employeeData = DataTransformer.getDatabaseEmployeesInsertingRowFrom(insertingData);
        resultSet.updateString(2, employeeData.getFirstName());
        resultSet.updateString(3, employeeData.getLastName());
        resultSet.updateDate(4, employeeData.getHireDate());
        resultSet.updateInt(5, employeeData.getProfessionId());
        resultSet.updateInt(6, employeeData.getSalary());
        resultSet.updateInt(7, employeeData.getAge());
        resultSet.insertRow();
        connection.commit();
    }

    public static void insertDepartmentsRow(ArrayList<String> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        DepartmentData departmentData = DataTransformer.getDatabaseDepartmentsInsertingRowFrom(insertingData);
        resultSet.updateString(2, departmentData.getDepartmentName());
        resultSet.updateInt(3, departmentData.getManagerId());
        resultSet.insertRow();
        connection.commit();
    }

    public static void insertProfessionsRow(ArrayList<String> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        ProfessionData professionData = DataTransformer.getDatabaseProfessionsInsertingRowFrom(insertingData);
        resultSet.updateString(2, professionData.getProfessionName());
        resultSet.updateInt(3, professionData.getManagementAbility());
        resultSet.updateInt(4, professionData.getDepartmentId());
        resultSet.insertRow();
        connection.commit();
    }

    public static void deleteRow(int row, ResultSet resultSet) throws SQLException {
        resultSet.absolute(row);
        resultSet.deleteRow();
        connection.commit();
    }
}

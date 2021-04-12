package services;

import common.DatabaseConnection;
import common.entities.*;

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

    public static void updateEquipmentRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        EquipmentData equipmentData = DataTransformer.getDatabaseEquipmentExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateString(2, equipmentData.getEquipmentName());
        resultSet.updateString(3, equipmentData.getEquipmentType());
        resultSet.updateInt(4, equipmentData.getDepartmentId());
        resultSet.updateRow();
        connection.commit();
    }

    public static void updateProjectsRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        ProjectData projectData = DataTransformer.getDatabaseProjectExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateInt(2, projectData.getProjectManager());
        resultSet.updateInt(3, projectData.getProjectCost());
        resultSet.updateDate(4, projectData.getStartDate());
        resultSet.updateDate(5, projectData.getFinishDate());
        resultSet.updateRow();
        connection.commit();
    }

    public static void updateContractsRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        ContractData contractData = DataTransformer.getDatabaseContractExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateInt(2, contractData.getContractManager());
        resultSet.updateDate(3, contractData.getStartDate());
        resultSet.updateDate(4, contractData.getFinishDate());
        resultSet.updateRow();
        connection.commit();
    }

    public static void updateSubcontractsRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        SubcontractData subcontractData = DataTransformer.getDatabaseSubcontractExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateString(2, subcontractData.getSubcontractorName());
        resultSet.updateDate(3, subcontractData.getStartDate());
        resultSet.updateDate(4, subcontractData.getFinishDate());
        resultSet.updateRow();
        connection.commit();
    }

    public static void updateEquipmentTypeRow(int row, ArrayList<String> rowData, ResultSet resultSet) throws SQLException {
        EquipmentTypeData equipmentTypeData = DataTransformer.getDatabaseEquipmentTypeExistingRowFrom(rowData);
        resultSet.absolute(row);
        resultSet.updateString(2, equipmentTypeData.getEquipmentTypeName());
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

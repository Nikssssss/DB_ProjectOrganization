package services;

import common.DatabaseConnection;
import common.entities.*;

import java.sql.*;
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
        resultSet.updateInt(3, equipmentData.getEquipmentTypeId());
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

    public static void insertEmployeesRow(ArrayList<String> insertingData, String employeeCategory, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        EmployeeData employeeData = DataTransformer.getDatabaseEmployeesInsertingRowFrom(insertingData);
        resultSet.updateString(2, employeeData.getFirstName());
        resultSet.updateString(3, employeeData.getLastName());
        resultSet.updateDate(4, employeeData.getHireDate());
        resultSet.updateInt(5, employeeData.getProfessionId());
        resultSet.updateInt(6, employeeData.getSalary());
        resultSet.updateInt(7, employeeData.getAge());
        resultSet.insertRow();

        resultSet.moveToCurrentRow();
        int insertedEmployeeId = resultSet.getInt(1);
        String categoryInsertQuery = "";
        String categoryField = insertingData.get(insertingData.size() - 1);
        switch (employeeCategory) {
            case "Техник":
                String equipmentId = DataTransformer.getDatabaseEquipmentTypeIdBy(categoryField);
                categoryInsertQuery = "insert into technics values(" +
                        insertedEmployeeId + ", " + equipmentId + ")";
                break;
            case "Инженер":
                categoryInsertQuery = "insert into engineers values(" +
                        insertedEmployeeId + ", '" + categoryField + "')";
                break;
            case "Конструктор":
                categoryInsertQuery = "insert into constructors values(" +
                        insertedEmployeeId + ", " + categoryField + ")";
                break;
            case "Бухгалтер":
                categoryInsertQuery = "insert into accountants values(" +
                        insertedEmployeeId + ", '" + categoryField + "')";
                break;
            case "Менеджер":
                categoryInsertQuery = "insert into managers values(" +
                        insertedEmployeeId + ", " + categoryField + ")";
                break;
        }
        Statement statement = connection.createStatement();
        statement.execute(categoryInsertQuery);

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

    public static void insertEquipmentTypeRow(ArrayList<String> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        EquipmentTypeData equipmentTypeData = DataTransformer.getDatabaseEquipmentTypeInsertingRowFrom(insertingData);
        resultSet.updateString(2, equipmentTypeData.getEquipmentTypeName());
        resultSet.insertRow();
        connection.commit();
    }

    public static void insertEquipmentRow(ArrayList<String> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        EquipmentData equipmentData = DataTransformer.getDatabaseEquipmentInsertingRowFrom(insertingData);
        resultSet.updateString(2, equipmentData.getEquipmentName());
        resultSet.updateInt(3, equipmentData.getEquipmentTypeId());
        resultSet.updateInt(4, equipmentData.getDepartmentId());
        resultSet.insertRow();
        connection.commit();
    }

    public static void insertContractRow(ArrayList<String> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        ContractData contractData = DataTransformer.getDatabaseContractInsertingRowFrom(insertingData);
        resultSet.updateInt(2, contractData.getContractManager());
        resultSet.updateDate(3, contractData.getStartDate());
        resultSet.updateDate(4, contractData.getFinishDate());
        resultSet.insertRow();
        connection.commit();
    }

    public static void insertSubcontractRow(ArrayList<String> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        SubcontractData subcontractData = new SubcontractData(null,
                insertingData.get(0),
                Date.valueOf(insertingData.get(1)),
                Date.valueOf(insertingData.get(2)));
        resultSet.updateString(2, subcontractData.getSubcontractorName());
        resultSet.updateDate(3, subcontractData.getStartDate());
        resultSet.updateDate(4, subcontractData.getFinishDate());
        resultSet.insertRow();
        connection.commit();
    }

    public static void insertProjectContractRow(ArrayList<Object> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        InsertingProjectData insertingProjectData = DataTransformer.getDatabaseInsertingProjectContractRowFrom(insertingData);
        resultSet.updateInt(2, insertingProjectData.getManagerId());
        resultSet.updateInt(3, insertingProjectData.getProjectCost());
        resultSet.updateDate(4, insertingProjectData.getStartDate());
        resultSet.updateDate(5, insertingProjectData.getFinishDate());
        resultSet.insertRow();
        connection.commit();

        resultSet.moveToCurrentRow();
        int projectId = resultSet.getInt(1);

        Statement statement = connection.createStatement();
        String query;
        for (Integer employeeId: insertingProjectData.getEmployees()) {
            query = "insert into projects_employees values(" + employeeId + ", " + projectId + ")";
            statement.execute(query);
        }

        for (Integer equipmentId: insertingProjectData.getEquipment()) {
            query = "insert into equipment_projects values(" + equipmentId + ", " + projectId + ")";
            statement.execute(query);
        }

        query = "insert into projects_contracts values(" + projectId + ", " + insertingProjectData.getContractId() + ")";
        statement.execute(query);

        connection.commit();
        statement.close();
    }

    public static void insertProjectSubcontractRow(ArrayList<Object> insertingData, ResultSet resultSet) throws SQLException {
        resultSet.moveToInsertRow();
        InsertingProjectData insertingProjectData = DataTransformer.getDatabaseInsertingProjectSubcontractRowFrom(insertingData);
        resultSet.updateInt(2, insertingProjectData.getManagerId());
        resultSet.updateInt(3, insertingProjectData.getProjectCost());
        resultSet.updateDate(4, insertingProjectData.getStartDate());
        resultSet.updateDate(5, insertingProjectData.getFinishDate());
        resultSet.insertRow();

        resultSet.moveToCurrentRow();
        int projectId = resultSet.getInt(1);

        Statement statement = connection.createStatement();
        String query = "insert into subcontracts_projects values(" + projectId + ", " + insertingProjectData.getContractId() + ")";
        statement.execute(query);

        connection.commit();
        statement.close();
    }

    public static void addExistingContractProject(ArrayList<Object> insertingData) throws SQLException {
        Statement statement = connection.createStatement();
        String projectId = (String) insertingData.get(0);
        String contractId = (String) insertingData.get(1);
        String query = "insert into projects_contracts values(" + projectId + ", " + contractId + ")";
        statement.execute(query);
        connection.commit();
        statement.close();
    }

    public static void addExistingSubcontractProject(ArrayList<Object> insertingData) throws SQLException {
        Statement statement = connection.createStatement();
        String projectId = (String) insertingData.get(0);
        String contractId = (String) insertingData.get(1);
        String query = "insert into subcontracts_projects values(" + projectId + ", " + contractId + ")";
        statement.execute(query);
        connection.commit();
        statement.close();
    }

    public static void deleteRow(int row, ResultSet resultSet) throws SQLException {
        resultSet.absolute(row);
        resultSet.deleteRow();
        connection.commit();
    }
}
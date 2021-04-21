package modules.tables;

import modules.tables.enums.TableType;
import services.DataTransformer;
import services.DataUpdater;
import services.QueriesExecutor;

import java.sql.*;
import java.util.ArrayList;

public class TablesInteractor {
    private ResultSet currentTableResultSet;
    private ResultSetMetaData currentTableResultSetMetaData;

    public ArrayList<ArrayList<String>> getColumnsAndRowsFromTable(TableType tableType) throws SQLException {
        String allTableColumns = QueriesExecutor.getAllColumnNamesOfTable(tableType);
        this.currentTableResultSet = QueriesExecutor.getResultSetFromTable(tableType, allTableColumns);
        this.currentTableResultSetMetaData = this.currentTableResultSet.getMetaData();

        return getColumnsAndRowsFromCurrentTable(tableType);
    }

    public ArrayList<ArrayList<String>> getColumnsAndRowsFromCurrentTable(TableType tableType) throws SQLException {
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

        switch (tableType) {
            case EMPLOYEES: {
                resultData = DataTransformer.getReadableColumnsAndRowsForEmployees(resultData);
                break;
            }
            case DEPARTMENTS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForDepartments(resultData);
                break;
            }
            case PROFESSIONS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForProfessions(resultData);
                break;
            }
            case EQUIPMENT: {
                resultData = DataTransformer.getReadableColumnsAndRowsForEquipment(resultData);
                break;
            }
            case PROJECTS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForProjects(resultData);
                break;
            }
            case CONTRACTS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForContracts(resultData);
                break;
            }
            case SUBCONTRACTS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForSubcontracts(resultData);
                break;
            }
            case EQUIPMENTTYPE: {
                resultData = DataTransformer.getReadableColumnsAndRowsForEquipmentType(resultData);
                break;
            }
            case PROJECTS_EMPLOYEES: {
                resultData = DataTransformer.getReadableColumnsAndRowsForProjectsEmployees(resultData);
                break;
            }
            case EQUIPMENT_PROJECTS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForEquipmentProjects(resultData);
                break;
            }
            case TECHNICS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForTechnics(resultData);
                break;
            }
            case ENGINEERS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForEngineers(resultData);
                break;
            }
            case MANAGERS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForManagers(resultData);
                break;
            }
            case ACCOUNTANTS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForAccountants(resultData);
                break;
            }
            case CONSTRUCTORS: {
                resultData = DataTransformer.getReadableColumnsAndRowsForConstructors(resultData);
                break;
            }
        }

        return resultData;
    }

    public ArrayList<ArrayList<String>> getColumnsDropDownListDataForTable(TableType tableType) throws SQLException {
        switch (tableType) {
            case EMPLOYEES: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> professions = DataTransformer.getReadableProfessionIDs();
                dropDownListData.add(professions);
                return dropDownListData;
            }
            case DEPARTMENTS:
            case CONTRACTS:
            case PROJECTS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> managers = DataTransformer.getReadableProjectAndContractManagerIDs();
                managers.add("-");
                dropDownListData.add(managers);
                return dropDownListData;
            }
            case PROFESSIONS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> managementAbilities = DataTransformer.getReadableManagerAbilities();
                ArrayList<String> departments = DataTransformer.getReadableDepartmentIDs();
                dropDownListData.add(managementAbilities);
                dropDownListData.add(departments);
                return dropDownListData;
            }
            case EQUIPMENT: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> departments = DataTransformer.getReadableDepartmentIDs();
                ArrayList<String> equipmentTypes = QueriesExecutor.getAllEquipmentTypes();
                dropDownListData.add(equipmentTypes);
                dropDownListData.add(departments);
                return dropDownListData;
            }
            case TECHNICS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> equipmentTypes = QueriesExecutor.getAllEquipmentTypes();
                dropDownListData.add(equipmentTypes);
                return dropDownListData;
            }
            default: {
                return null;
            }
        }
    }

    public void updateRow(int row, ArrayList<String> rowData, TableType tableType) throws SQLException {
        switch (tableType) {
            case EMPLOYEES: {
                DataUpdater.updateEmployeesRow(row, rowData, currentTableResultSet);
                break;
            }
            case DEPARTMENTS: {
                DataUpdater.updateDepartmentsRow(row, rowData, currentTableResultSet);
                break;
            }
            case PROFESSIONS: {
                DataUpdater.updateProfessionsRow(row, rowData, currentTableResultSet);
                break;
            }
            case EQUIPMENT: {
                DataUpdater.updateEquipmentRow(row, rowData, currentTableResultSet);
                break;
            }
            case PROJECTS: {
                DataUpdater.updateProjectsRow(row, rowData, currentTableResultSet);
                break;
            }
            case CONTRACTS: {
                DataUpdater.updateContractsRow(row, rowData, currentTableResultSet);
                break;
            }
            case SUBCONTRACTS: {
                DataUpdater.updateSubcontractsRow(row, rowData, currentTableResultSet);
                break;
            }
            case EQUIPMENTTYPE: {
                DataUpdater.updateEquipmentTypeRow(row, rowData, currentTableResultSet);
                break;
            }
            case PROJECTS_EMPLOYEES: {
                DataUpdater.updateProjectsEmployeesRow(row, rowData, currentTableResultSet);
                break;
            }
            case TECHNICS: {
                DataUpdater.updateTechnicsRow(row, rowData, currentTableResultSet);
                break;
            }
            case CONSTRUCTORS:
            case ACCOUNTANTS:
            case MANAGERS:
            case ENGINEERS: {
                DataUpdater.updateEmployeeCategoryRow(row, rowData, currentTableResultSet);
                break;
            }
        }
    }

    public void deleteRow(int row) throws SQLException {
        DataUpdater.deleteRow(row, currentTableResultSet);
    }

    public ResultSet getCurrentTableResultSet() {
        return currentTableResultSet;
    }

}

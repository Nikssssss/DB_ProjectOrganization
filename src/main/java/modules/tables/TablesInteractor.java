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
            case DEPARTMENTS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> managers = DataTransformer.getReadableManagerIDs();
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
        }
    }

    public void deleteRow(int row) throws SQLException {
        DataUpdater.deleteRow(row, currentTableResultSet);
    }

    public ResultSet getCurrentTableResultSet() {
        return currentTableResultSet;
    }

}

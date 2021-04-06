package modules.adding;

import modules.tables.enums.TableType;
import services.DataTransformer;
import services.DataUpdater;

import java.sql.*;
import java.util.ArrayList;

public class AddingInteractor {
    private ResultSet resultSet;
    private TableType currentTableType;

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public void setCurrentTableType(TableType tableType) {
        this.currentTableType = tableType;
    }

    public TableType getCurrentTableType() {
        return currentTableType;
    }

    public ArrayList<ArrayList<String>> getColumnsDropDownListData() throws SQLException {
        switch (currentTableType) {
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

    public void insertRow(ArrayList<String> insertingData) throws SQLException, IllegalArgumentException {
        switch (currentTableType) {
            case EMPLOYEES: {
                DataUpdater.insertEmployeesRow(insertingData, resultSet);
                break;
            }
            case DEPARTMENTS: {
                DataUpdater.insertDepartmentsRow(insertingData, resultSet);
                break;
            }
            case PROFESSIONS: {
                DataUpdater.insertProfessionsRow(insertingData, resultSet);
                break;
            }
        }
    }

}

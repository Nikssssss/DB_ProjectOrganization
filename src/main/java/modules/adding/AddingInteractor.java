package modules.adding;

import modules.tables.enums.TableType;
import services.DataTransformer;
import services.DataUpdater;
import services.QueriesExecutor;

import java.sql.*;
import java.time.DateTimeException;
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
                ArrayList<String> equipmentTypes = DataTransformer.getReadableEquipmentTypeIDs();
                dropDownListData.add(professions);
                dropDownListData.add(equipmentTypes);
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
            case EQUIPMENT: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> equipmentTypes = DataTransformer.getReadableEquipmentTypeIDs();
                ArrayList<String> departments = DataTransformer.getReadableDepartmentIDs();
                dropDownListData.add(equipmentTypes);
                dropDownListData.add(departments);
                return dropDownListData;
            }
            case PROJECTS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> projects = QueriesExecutor.getAllProjects();
                ArrayList<String> contracts = QueriesExecutor.getAllContracts();
                ArrayList<String> subcontracts = QueriesExecutor.getAllSubcontracts();
                ArrayList<String> managers = DataTransformer.getReadableProjectAndContractManagerIDs();
                ArrayList<String> employees = DataTransformer.getReadableEmployeeIDs();
                ArrayList<String> equipment = DataTransformer.getReadableUnusedEquipmentIDs();
                dropDownListData.add(projects);
                dropDownListData.add(contracts);
                dropDownListData.add(subcontracts);
                dropDownListData.add(managers);
                dropDownListData.add(employees);
                dropDownListData.add(equipment);
                return dropDownListData;
            }
            case CONTRACTS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> managers = DataTransformer.getReadableProjectAndContractManagerIDs();
                dropDownListData.add(managers);
                return dropDownListData;
            }
            case PROJECTS_EMPLOYEES: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> employees = DataTransformer.getReadableEmployeeIDs();
                ArrayList<String> projects = QueriesExecutor.getAllProjects();
                dropDownListData.add(employees);
                dropDownListData.add(projects);
                return dropDownListData;
            }
            case EQUIPMENT_PROJECTS: {
                ArrayList<ArrayList<String>> dropDownListData = new ArrayList<>();
                ArrayList<String> equipment = DataTransformer.getReadableUnusedEquipmentIDs();
                ArrayList<String> projects = QueriesExecutor.getAllProjects();
                dropDownListData.add(equipment);
                dropDownListData.add(projects);
                return dropDownListData;
            }
            default: {
                return null;
            }
        }
    }

    public void insertRow(ArrayList<String> insertingData, String employeeCategory) throws SQLException, DateTimeException, IllegalArgumentException {
        switch (currentTableType) {
            case EMPLOYEES: {
                DataUpdater.insertEmployeesRow(insertingData, employeeCategory, resultSet);
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
            case EQUIPMENTTYPE: {
                DataUpdater.insertEquipmentTypeRow(insertingData, resultSet);
                break;
            }
            case EQUIPMENT: {
                DataUpdater.insertEquipmentRow(insertingData, resultSet);
                break;
            }
            case CONTRACTS: {
                if (!hasCorrectDateInterval(Date.valueOf(insertingData.get(2)), Date.valueOf(insertingData.get(3)))) {
                    throw new DateTimeException("Дата окончания <= дата начала");
                }
                DataUpdater.insertContractRow(insertingData, resultSet);
                break;
            }
            case SUBCONTRACTS: {
                if (!hasCorrectDateInterval(Date.valueOf(insertingData.get(2)), Date.valueOf(insertingData.get(3)))) {
                    throw new DateTimeException("Дата окончания <= дата начала");
                }
                DataUpdater.insertSubcontractRow(insertingData, resultSet);
                break;
            }
            case PROJECTS_EMPLOYEES: {
                DataUpdater.insertProjectsEmployeesRow(insertingData, resultSet);
                break;
            }
            case EQUIPMENT_PROJECTS: {
                DataUpdater.insertEquipmentProjectsRow(insertingData, resultSet);
                break;
            }
        }
    }

    public void insertProjectRow(ArrayList<Object> insertingData, boolean isOwnOrganization, boolean isNewProject) throws SQLException, DateTimeException {
        if (isOwnOrganization) {
            if (isNewProject) {
                if (!hasCorrectDateInterval(Date.valueOf((String) insertingData.get(6)), Date.valueOf((String) insertingData.get(7)))) {
                    throw new DateTimeException("Дата окончания <= дата начала");
                }
                insertingData.set(1, DataTransformer.getDatabaseContractIdBy((String) insertingData.get(1)));
                DataUpdater.insertProjectContractRow(insertingData, resultSet);
            } else {
                insertingData.set(0, DataTransformer.getDatabaseProjectIdBy((String) insertingData.get(0)));
                insertingData.set(1, DataTransformer.getDatabaseContractIdBy((String) insertingData.get(1)));
                DataUpdater.addExistingContractProject(insertingData);
            }
        } else {
            if (isNewProject) {
                if (!hasCorrectDateInterval(Date.valueOf((String) insertingData.get(4)), Date.valueOf((String) insertingData.get(5)))) {
                    throw new DateTimeException("Дата окончания <= дата начала");
                }
                insertingData.set(1, DataTransformer.getDatabaseSubcontractIdBy((String) insertingData.get(1)));
                DataUpdater.insertProjectSubcontractRow(insertingData, resultSet);
            } else {
                insertingData.set(0, DataTransformer.getDatabaseProjectIdBy((String) insertingData.get(0)));
                insertingData.set(1, DataTransformer.getDatabaseSubcontractIdBy((String) insertingData.get(1)));
                DataUpdater.addExistingSubcontractProject(insertingData);
            }
        }
    }

    public ArrayList<String> getEmployeeCategoryDropDownListData(String category) throws SQLException {
        if (category.equals("Техник")) {
            return DataTransformer.getReadableEquipmentTypeIDs();
        } else {
            return null;
        }
    }

    //MARK: private methods

    private boolean hasCorrectDateInterval(Date startDate, Date finishDate) {
        return finishDate.getTime() > startDate.getTime();
    }

}

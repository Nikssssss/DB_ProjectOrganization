package services;

import common.entities.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataTransformer {
    private DataTransformer() {}

    public static ArrayList<String> getReadableProfessionIDs() throws SQLException {
        return QueriesExecutor.getAllProfessionNames();
    }

    public static ArrayList<String> getReadableManagerIDs() throws SQLException {
        return QueriesExecutor.getAllManagers();
    }

    public static ArrayList<String> getReadableProjectAndContractManagerIDs() throws SQLException {
        return QueriesExecutor.getAllProjectAndContractManagers();
    }

    public static ArrayList<String> getReadableEmployeeIDs() throws SQLException {
        return QueriesExecutor.getAllEmployees();
    }

    public static ArrayList<String> getReadableDepartmentIDs() throws SQLException {
        return QueriesExecutor.getAllDepartments();
    }

    public static ArrayList<String> getReadableManagerAbilities() {
        ArrayList<String> managementAbilities = new ArrayList<>();
        managementAbilities.add("Да");
        managementAbilities.add("Нет");
        return managementAbilities;
    }

    public static ArrayList<String> getReadableEquipmentTypeIDs() throws SQLException {
        return QueriesExecutor.getAllEquipmentTypes();
    }

    public static ArrayList<String> getReadableUnusedEquipmentIDs() throws SQLException {
        return QueriesExecutor.getAllUnusedEquipment();
    }

    public static String getReadableEquipmentIdBy(String equipmentId) throws SQLException {
        return QueriesExecutor.getEquipmentNameBy(equipmentId);
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForEmployees(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(4, "PROFESSIONS");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(3, rowData.get(3).substring(0, 10));
            rowData.set(4, getReadableProfessionIDBy(rowData.get(4)));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForDepartments(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(2, "MANAGER");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            if (rowData.get(2) != null) {
                rowData.set(2, getReadableManagerIDBy(rowData.get(2)));
            } else {
                rowData.set(2, "-");
            }
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForProfessions(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(3, "DEPARTMENT");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(2, getReadableManagementAbilityBy(rowData.get(2)));
            rowData.set(3, getReadableDepartmentIDBy(rowData.get(3)));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForEquipment(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(2, "EQUIPMENT_TYPE");
        columnNames.set(3, "DEPARTMENT");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(2, getReadableEquipmentTypeIDBy(rowData.get(2)));
            rowData.set(3, getReadableDepartmentIDBy(rowData.get(3)));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForProjects(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(0, "ID проекта");
        columnNames.set(1, "Название проекта");
        columnNames.set(2, "Менеджер");
        columnNames.set(3, "Цена");
        columnNames.set(4, "Дата начала");
        columnNames.set(5, "Дата окончания");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(2, getReadableManagerIDBy(rowData.get(2)));
            rowData.set(4, rowData.get(4).substring(0, 10));
            rowData.set(5, rowData.get(5).substring(0, 10));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForContracts(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(0, "ID договора");
        columnNames.set(1, "Название договора");
        columnNames.set(2, "Менеджер");
        columnNames.set(3, "Дата начала");
        columnNames.set(4, "Дата окончания");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(2, getReadableManagerIDBy(rowData.get(2)));
            rowData.set(3, rowData.get(3).substring(0, 10));
            rowData.set(4, rowData.get(4).substring(0, 10));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForSubcontracts(ArrayList<ArrayList<String>> databaseData) {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(0, "ID субдоговора");
        columnNames.set(1, "Название субдоговора");
        columnNames.set(2, "Название субподрядчика");
        columnNames.set(3, "Дата начала");
        columnNames.set(4, "Дата окончания");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(3, rowData.get(3).substring(0, 10));
            rowData.set(4, rowData.get(4).substring(0, 10));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForEquipmentType(ArrayList<ArrayList<String>> databaseData) {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        readableData.addAll(rowsData);

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForProjectsEmployees(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(0, "Сотрудник");
        columnNames.set(1, "Проект");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(0, getReadableManagerIDBy(rowData.get(0)));
            rowData.set(1, getReadableProjectIdBy(rowData.get(1)));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static ArrayList<ArrayList<String>> getReadableColumnsAndRowsForEquipmentProjects(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(0, "Оборудование");
        columnNames.set(1, "Проект");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(0, getReadableEquipmentIdBy(rowData.get(0)));
            rowData.set(1, getReadableProjectIdBy(rowData.get(1)));
            readableData.add(rowData);
        }

        return readableData;
    }

    public static EmployeeData getDatabaseEmployeesExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(4, getDatabaseProfessionIdBy(readableRowData.get(4)));
        return new EmployeeData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                readableRowData.get(2),
                Date.valueOf(readableRowData.get(3)),
                Integer.parseInt(readableRowData.get(4)),
                Integer.parseInt(readableRowData.get(5)),
                Integer.parseInt(readableRowData.get(6)));
    }

    public static DepartmentData getDatabaseDepartmentsExistingRowFrom(ArrayList<String> readableRowData) {
        if (readableRowData.get(2).equals("-")) {
            readableRowData.set(2, null);
            return new DepartmentData(Integer.parseInt(readableRowData.get(0)),
                    readableRowData.get(1),
                    null);
        } else {
            readableRowData.set(2, getDatabaseManagerIdBy(readableRowData.get(2)));
            return new DepartmentData(Integer.parseInt(readableRowData.get(0)),
                    readableRowData.get(1),
                    Integer.parseInt(readableRowData.get(2)));
        }
    }

    public static ProfessionData getDatabaseProfessionsExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(2, getDatabaseManagementAbilityFrom(readableRowData.get(2)));
        readableRowData.set(3, getDatabaseDepartmentIdBy(readableRowData.get(3)));
        return new ProfessionData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                Integer.parseInt(readableRowData.get(2)),
                Integer.parseInt(readableRowData.get(3)));
    }

    public static EquipmentData getDatabaseEquipmentExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(2, getDatabaseEquipmentTypeIdBy(readableRowData.get(2)));
        readableRowData.set(3, getDatabaseDepartmentIdBy(readableRowData.get(3)));
        return new EquipmentData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                Integer.parseInt(readableRowData.get(2)),
                Integer.parseInt(readableRowData.get(3)));
    }

    public static ProjectData getDatabaseProjectExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(2, getDatabaseManagerIdBy(readableRowData.get(2)));
        return new ProjectData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                Integer.parseInt(readableRowData.get(2)),
                Integer.parseInt(readableRowData.get(3)),
                Date.valueOf(readableRowData.get(4)),
                Date.valueOf(readableRowData.get(5)));
    }

    public static ContractData getDatabaseContractExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(2, getDatabaseManagerIdBy(readableRowData.get(2)));
        return new ContractData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                Integer.parseInt(readableRowData.get(2)),
                Date.valueOf(readableRowData.get(3)),
                Date.valueOf(readableRowData.get(4)));
    }

    public static SubcontractData getDatabaseSubcontractExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        return new SubcontractData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                readableRowData.get(2),
                Date.valueOf(readableRowData.get(3)),
                Date.valueOf(readableRowData.get(4)));
    }

    public static EquipmentTypeData getDatabaseEquipmentTypeExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        return new EquipmentTypeData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1));
    }

    public static ProjectsEmployeesData getDatabaseProjectEmployeesExistingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(0, getDatabaseManagerIdBy(readableRowData.get(0)));
        readableRowData.set(1, getDatabaseProjectIdBy(readableRowData.get(1)));
        return new ProjectsEmployeesData(Integer.parseInt(readableRowData.get(0)),
                Integer.parseInt(readableRowData.get(1)));
    }

    public static EmployeeData getDatabaseEmployeesInsertingRowFrom(ArrayList<String> readableRowData) throws SQLException, IllegalArgumentException {
        readableRowData.set(3, getDatabaseProfessionIdBy(readableRowData.get(3)));
        return new EmployeeData(null,
                readableRowData.get(0),
                readableRowData.get(1),
                Date.valueOf(readableRowData.get(2)),
                Integer.parseInt(readableRowData.get(3)),
                Integer.parseInt(readableRowData.get(4)),
                Integer.parseInt(readableRowData.get(5)));
    }

    public static DepartmentData getDatabaseDepartmentsInsertingRowFrom(ArrayList<String> readableRowData) {
        readableRowData.set(1, getDatabaseManagerIdBy(readableRowData.get(1)));
        return new DepartmentData(null,
                readableRowData.get(0),
                Integer.parseInt(readableRowData.get(1)));
    }

    public static ProfessionData getDatabaseProfessionsInsertingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(1, getDatabaseManagementAbilityFrom(readableRowData.get(1)));
        readableRowData.set(2, getDatabaseDepartmentIdBy(readableRowData.get(2)));
        return new ProfessionData(null,
                readableRowData.get(0),
                Integer.parseInt(readableRowData.get(1)),
                Integer.parseInt(readableRowData.get(2)));
    }

    public static EquipmentTypeData getDatabaseEquipmentTypeInsertingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        return new EquipmentTypeData(null,
                readableRowData.get(0));
    }

    public static EquipmentData getDatabaseEquipmentInsertingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(1, getDatabaseEquipmentTypeIdBy(readableRowData.get(1)));
        readableRowData.set(2, getDatabaseDepartmentIdBy(readableRowData.get(2)));
        return new EquipmentData(null,
                readableRowData.get(0),
                Integer.parseInt(readableRowData.get(1)),
                Integer.parseInt(readableRowData.get(2)));
    }

    public static ContractData getDatabaseContractInsertingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(1, getDatabaseManagerIdBy(readableRowData.get(1)));
        return new ContractData(null,
                readableRowData.get(0),
                Integer.parseInt(readableRowData.get(1)),
                Date.valueOf(readableRowData.get(2)),
                Date.valueOf(readableRowData.get(3)));
    }

    public static ProjectsEmployeesData getDatabaseProjectEmployeesInsertingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(0, getDatabaseManagerIdBy(readableRowData.get(0)));
        readableRowData.set(1, getDatabaseProjectIdBy(readableRowData.get(1)));
        return new ProjectsEmployeesData(Integer.parseInt(readableRowData.get(0)),
                Integer.parseInt(readableRowData.get(1)));
    }

    public static EquipmentProjectsData getDatabaseEquipmentProjectsInsertingRowFrom(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(0, getDatabaseEquipmentIdBy(readableRowData.get(0)));
        readableRowData.set(1, getDatabaseProjectIdBy(readableRowData.get(1)));
        return new EquipmentProjectsData(Integer.parseInt(readableRowData.get(0)),
                Integer.parseInt(readableRowData.get(1)));
    }

    public static InsertingProjectData getDatabaseInsertingProjectContractRowFrom(ArrayList<Object> readableRowData) throws SQLException {
        String projectName = (String) readableRowData.get(0);
        Integer contractId = Integer.parseInt((String) readableRowData.get(1));
        Integer managerId = Integer.parseInt(DataTransformer.getDatabaseManagerIdBy((String)readableRowData.get(2)));
        ArrayList<Integer> employees = new ArrayList<>();
        for (String employee: (ArrayList<String>) readableRowData.get(3)) {
            employees.add(Integer.parseInt(getDatabaseManagerIdBy(employee)));
        }
        ArrayList<Integer> equipment = new ArrayList<>();
        for (String equipmentElement: (ArrayList<String>) readableRowData.get(4)) {
            equipment.add(Integer.parseInt(getDatabaseEquipmentIdBy(equipmentElement)));
        }
        Integer projectCost = Integer.parseInt((String) readableRowData.get(5));
        Date startDate = Date.valueOf((String) readableRowData.get(6));
        Date finishDate = Date.valueOf((String) readableRowData.get(7));
        return new InsertingProjectData(null, projectName, contractId, managerId, employees, equipment,
                projectCost, startDate, finishDate);
    }

    public static InsertingProjectData getDatabaseInsertingProjectSubcontractRowFrom(ArrayList<Object> readableRowData) {
        String projectName = (String) readableRowData.get(0);
        Integer contractId = Integer.parseInt((String) readableRowData.get(1));
        Integer managerId = Integer.parseInt(DataTransformer.getDatabaseManagerIdBy((String)readableRowData.get(2)));
        Integer projectCost = Integer.parseInt((String) readableRowData.get(3));
        Date startDate = Date.valueOf((String) readableRowData.get(4));
        Date finishDate = Date.valueOf((String) readableRowData.get(5));
        return new InsertingProjectData(null, projectName, contractId, managerId, null, null,
                projectCost, startDate, finishDate);
    }

    public static String getDatabaseProfessionIdBy(String professionName) throws SQLException {
        return QueriesExecutor.getProfessionIdBy(professionName);
    }

    public static String getDatabaseEquipmentTypeIdBy(String equipmentType) throws SQLException {
        return QueriesExecutor.getEquipmentTypeIdBy(equipmentType);
    }

    public static String getDatabaseProjectIdBy(String projectName) throws SQLException {
        return QueriesExecutor.getProjectIdBy(projectName);
    }

    public static String getDatabaseContractIdBy(String contractName) throws SQLException {
        return QueriesExecutor.getContractIdBy(contractName);
    }

    public static String getDatabaseSubcontractIdBy(String subcontractName) throws SQLException {
        return QueriesExecutor.getSubcontractIdBy(subcontractName);
    }

    //MARK: private methods

    private static String getReadableProfessionIDBy(String professionId) throws SQLException {
        return QueriesExecutor.getProfessionNameBy(professionId);
    }

    private static String getReadableManagerIDBy(String managerId) throws SQLException {
        return QueriesExecutor.getManagerInfoBy(managerId);
    }

    private static String getReadableDepartmentIDBy(String departmentId) throws SQLException {
        return QueriesExecutor.getDepartmentNameBy(departmentId);
    }

    private static String getReadableManagementAbilityBy(String managementAbility) {
        return managementAbility.equals("1") ? "Да" : "Нет";
    }

    private static String getReadableEquipmentTypeIDBy(String equipmentId) throws SQLException {
        return QueriesExecutor.getEquipmentTypeNameBy(equipmentId);
    }

    private static String getReadableProjectIdBy(String projectId) throws SQLException {
        return QueriesExecutor.getProjectNameBy(projectId);
    }

    private static String getDatabaseManagerIdBy(String managerInfo) {
        return managerInfo.substring(0, managerInfo.indexOf(":"));
    }

    private static String getDatabaseDepartmentIdBy(String departmentName) throws SQLException {
        return QueriesExecutor.getDepartmentIdBy(departmentName);
    }

    private static String getDatabaseManagementAbilityFrom(String managementAbility) {
        return managementAbility.equals("Да") ? "1" : "0";
    }

    private static String getDatabaseEquipmentIdBy(String readableEquipment) throws SQLException {
        if (readableEquipment.contains(":")) {
            return readableEquipment.substring(0, readableEquipment.indexOf(":"));
        } else {
            return QueriesExecutor.getEquipmentIdBy(readableEquipment);
        }
    }

}

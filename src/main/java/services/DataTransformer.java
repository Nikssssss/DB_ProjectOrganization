package services;

import common.entities.DepartmentData;
import common.entities.EmployeeData;
import common.entities.ProfessionData;

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

    public static ArrayList<String> getReadableDepartmentIDs() throws SQLException {
        return QueriesExecutor.getAllDepartments();
    }

    public static ArrayList<String> getReadableManagerAbilities() {
        ArrayList<String> managementAbilities = new ArrayList<>();
        managementAbilities.add("Да");
        managementAbilities.add("Нет");
        return managementAbilities;
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

    public static String getDatabaseProfessionIdBy(String professionName) throws SQLException {
        return QueriesExecutor.getProfessionIdBy(professionName);
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

    private static String getDatabaseManagerIdBy(String managerInfo) {
        return managerInfo.substring(0, managerInfo.indexOf(":"));
    }

    private static String getDatabaseDepartmentIdBy(String departmentName) throws SQLException {
        return QueriesExecutor.getDepartmentIdBy(departmentName);
    }

    private static String getDatabaseManagementAbilityFrom(String managementAbility) {
        return managementAbility.equals("Да") ? "1" : "0";
    }

}

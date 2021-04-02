package services;

import modules.tables.entities.DepartmentData;
import modules.tables.entities.EmployeeData;
import modules.tables.entities.ProfessionData;
import modules.tables.enums.TableType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataHandlerService {
    private final Connection connection;

    public DataHandlerService(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<ArrayList<String>> getReadableDataFrom(ArrayList<ArrayList<String>> databaseData, TableType tableType) throws SQLException {
        switch (tableType) {
            case EMPLOYEES: {
                return this.getReadableDataForEmployees(databaseData);
            }
            case DEPARTMENTS: {
                return this.getReadableDataForDepartments(databaseData);
            }
            case PROFESSIONS: {
                return this.getReadableDataForProfessions(databaseData);
            }
            default: {
                return databaseData;
            }
        }
    }

    public EmployeeData getDatabaseRowDataFromEmployees(ArrayList<String> readableRowData) throws SQLException, IllegalArgumentException {
        if (readableRowData.size() == 7) {
            return this.getDatabaseRowDataForEmployees(readableRowData);
        } else {
            return this.getDatabaseInsertingRowDataForEmployees(readableRowData);
        }
    }

    public DepartmentData getDatabaseRowDataFromDepartments(ArrayList<String> readableRowData) {
        if (readableRowData.size() == 3) {
            return this.getDatabaseRowDataForDepartments(readableRowData);
        } else {
            return this.getDatabaseInsertingRowDataForDepartments(readableRowData);
        }
    }

    public ProfessionData getDatabaseRowDataFromProfessions(ArrayList<String> readableRowData) throws SQLException {
        if (readableRowData.size() == 4) {
            return this.getDatabaseRowDataForProfessions(readableRowData);
        } else {
            return this.getDatabaseInsertingRowDataForProfessions(readableRowData);
        }
    }

    public String getProfessionIdBy(String professionName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_id from professions where profession_name = '" + professionName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    //MARK: private methods

    private ArrayList<ArrayList<String>> getReadableDataForEmployees(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(4, "PROFESSIONS");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(3, rowData.get(3).substring(0, 10));
            rowData.set(4, this.getProfessionNameBy(rowData.get(4)));
            readableData.add(rowData);
        }

        return readableData;
    }

    private ArrayList<ArrayList<String>> getReadableDataForDepartments(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(2, "MANAGER");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            if (rowData.get(2) != null) {
                rowData.set(2, this.getManagerInfoBy(rowData.get(2)));
            } else {
                rowData.set(2, "-");
            }
            readableData.add(rowData);
        }

        return readableData;
    }

    private ArrayList<ArrayList<String>> getReadableDataForProfessions(ArrayList<ArrayList<String>> databaseData) throws SQLException {
        ArrayList<ArrayList<String>> readableData = new ArrayList<>();
        ArrayList<String> columnNames = databaseData.get(0);
        columnNames.set(3, "DEPARTMENT");
        readableData.add(columnNames);

        List<ArrayList<String>> rowsData = databaseData.subList(1, databaseData.size());
        for (ArrayList<String> rowData: rowsData) {
            rowData.set(2, this.getReadableManagementAbility(rowData.get(2)));
            rowData.set(3, this.getDepartmentNameBy(rowData.get(3)));
            readableData.add(rowData);
        }

        return readableData;
    }

    private String getProfessionNameBy(String professionId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from professions where profession_id = " + professionId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    private String getManagerInfoBy(String managerId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select employee_id, first_name, last_name from employees where employee_id = " + managerId;
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            String result = resultSet.getString(1) + ": " + resultSet.getString(2) + " " + resultSet.getString(3);
            resultSet.close();
            statement.close();
            return result;
        } else {
            return "-";
        }
    }

    private String getDepartmentNameBy(String departmentId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_name from departments where department_id = " + departmentId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    private String getReadableManagementAbility(String managementAbility) {
        return managementAbility.equals("1") ? "Да" : "Нет";
    }

    private EmployeeData getDatabaseRowDataForEmployees(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(4, this.getProfessionIdBy(readableRowData.get(4)));
        return new EmployeeData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                readableRowData.get(2),
                Date.valueOf(readableRowData.get(3)),
                Integer.parseInt(readableRowData.get(4)),
                Integer.parseInt(readableRowData.get(5)),
                Integer.parseInt(readableRowData.get(6)));
    }

    private DepartmentData getDatabaseRowDataForDepartments(ArrayList<String> readableRowData) {
        if (readableRowData.get(2).equals("-")) {
            readableRowData.set(2, null);
        } else {
            readableRowData.set(2, this.getManagerIdBy(readableRowData.get(2)));
        }
        return new DepartmentData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                Integer.parseInt(readableRowData.get(2)));
    }

    private ProfessionData getDatabaseRowDataForProfessions(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(2, this.getManagementAbilityNumberFrom(readableRowData.get(2)));
        readableRowData.set(3, this.getDepartmentIdBy(readableRowData.get(3)));
        return new ProfessionData(Integer.parseInt(readableRowData.get(0)),
                readableRowData.get(1),
                Integer.parseInt(readableRowData.get(2)),
                Integer.parseInt(readableRowData.get(3)));
    }

    private String getManagerIdBy(String managerInfo) {
        return managerInfo.substring(0, managerInfo.indexOf(":"));
    }

    private String getDepartmentIdBy(String departmentName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select department_id from departments where department_name = '" + departmentName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        String result = resultSet.getString(1);
        resultSet.close();
        statement.close();
        return result;
    }

    private String getManagementAbilityNumberFrom(String managementAbility) {
        return managementAbility.equals("Да") ? "1" : "0";
    }

    private EmployeeData getDatabaseInsertingRowDataForEmployees(ArrayList<String> readableRowData) throws SQLException, IllegalArgumentException {
        readableRowData.set(3, this.getProfessionIdBy(readableRowData.get(3)));
        return new EmployeeData(null,
                readableRowData.get(0),
                readableRowData.get(1),
                Date.valueOf(readableRowData.get(2)),
                Integer.parseInt(readableRowData.get(3)),
                Integer.parseInt(readableRowData.get(4)),
                Integer.parseInt(readableRowData.get(5)));
    }

    private DepartmentData getDatabaseInsertingRowDataForDepartments(ArrayList<String> readableRowData) {
        readableRowData.set(1, this.getManagerIdBy(readableRowData.get(1)));
        return new DepartmentData(null,
                readableRowData.get(0),
                Integer.parseInt(readableRowData.get(1)));
    }

    private ProfessionData getDatabaseInsertingRowDataForProfessions(ArrayList<String> readableRowData) throws SQLException {
        readableRowData.set(1, this.getManagementAbilityNumberFrom(readableRowData.get(1)));
        readableRowData.set(2, this.getDepartmentIdBy(readableRowData.get(2)));
        return new ProfessionData(null,
                readableRowData.get(0),
                Integer.parseInt(readableRowData.get(1)),
                Integer.parseInt(readableRowData.get(2)));
    }
}

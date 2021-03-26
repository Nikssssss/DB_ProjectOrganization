package modules.tables.services;

import modules.tables.entities.EmployeeData;
import modules.tables.enums.TableType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataHandlerService {
    private Connection connection;

    public DataHandlerService(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<ArrayList<String>> getReadableDataFrom(ArrayList<ArrayList<String>> databaseData, TableType tableType) throws SQLException {
        switch (tableType) {
            case EMPLOYEES: {
                return this.getReadableDataForEmployees(databaseData);
            }
            default: {
                return databaseData;
            }
        }
    }

    public EmployeeData getDatabaseRowDataFromEmployees(ArrayList<String> readableRowData) throws SQLException {
        return this.getDatabaseRowDataForEmployees(readableRowData);
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

    private String getProfessionNameBy(String professionId) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from professions where profession_id = " + professionId;
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString(1);
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

    private String getProfessionIdBy(String professionName) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_id from professions where profession_name = '" + professionName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        return resultSet.getString(1);
    }
}

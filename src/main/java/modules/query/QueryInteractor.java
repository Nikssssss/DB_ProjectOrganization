package modules.query;

import modules.queries.enums.QueryType;
import modules.query.entities.OrganizationRosterData;
import modules.tables.enums.TableType;
import services.DataHandlerService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QueryInteractor {
    private Connection connection;
    private QueryType queryType;
    private DataHandlerService dataHandlerService;

    public void setConnection(Connection connection) {
        this.connection = connection;
        dataHandlerService = new DataHandlerService(connection);
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public Connection getConnection() {
        return connection;
    }

    public ArrayList<ArrayList<String>> getComboBoxData() throws SQLException {
        ArrayList<ArrayList<String>> comboBoxData = new ArrayList<>();
        switch (queryType) {
            case ORGANIZATION_ROSTER: {
                ArrayList<String> professions = this.getAllProfessions();
                professions.add(0, "Все профессии");
                comboBoxData.add(professions);
            }
        }
        return comboBoxData;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public ArrayList<String> getAllColumns() {
        switch (queryType) {
            case ORGANIZATION_ROSTER: {
                return this.getOrganizationRosterColumns();
            }
            default: {
                return null;
            }
        }
    }

    public ArrayList<ArrayList<String>> executeQuery(ArrayList<String> queryParameters) throws SQLException {
        switch (queryType) {
            case ORGANIZATION_ROSTER: {
                return this.executeOrganizationRosterQuery(queryParameters);
            }
            default: {
                return null;
            }
        }
    }

    //MARK: private methods

    private ArrayList<String> getAllProfessions() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select profession_name from professions";
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<String> professions = new ArrayList<>();
        while (resultSet.next()) {
            professions.add(resultSet.getString(1));
        }
        resultSet.close();
        statement.close();
        return professions;
    }

    private ArrayList<String> getOrganizationRosterColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("ID");
        organizationRosterColumns.add("Имя");
        organizationRosterColumns.add("Фамилия");
        organizationRosterColumns.add("Дата устройства");
        organizationRosterColumns.add("Профессия");
        organizationRosterColumns.add("Зарплата");
        organizationRosterColumns.add("Возраст");
        return organizationRosterColumns;
    }

    private String unionWordFor(String query) {
        if (query.endsWith(" where")) {
            return " ";
        } else {
            return " and ";
        }
    }

    private ArrayList<ArrayList<String>> executeOrganizationRosterQuery(ArrayList<String> queryParameters) throws SQLException {
        OrganizationRosterData organizationRosterData = new OrganizationRosterData(queryParameters.get(0),
                queryParameters.get(1),
                queryParameters.get(2),
                queryParameters.get(3),
                queryParameters.get(4),
                queryParameters.get(5),
                queryParameters.get(6));
        String query = "select * from employees where";
        if (!organizationRosterData.getProfession().equals("Все профессии")) {
            query += " profession_id = " + dataHandlerService.getProfessionIdBy(organizationRosterData.getProfession());
        }
        if (organizationRosterData.getMinHireDate() != null && !organizationRosterData.getMinHireDate().equals("")) {
            query += unionWordFor(query) + "hire_date >= to_date('" + organizationRosterData.getMinHireDate() + "', 'yyyy-mm-dd')";
        }
        if (organizationRosterData.getMaxHireDate() != null && !organizationRosterData.getMaxHireDate().equals("")) {
            query += unionWordFor(query) + "hire_date >= to_date('" + organizationRosterData.getMaxHireDate() + "', 'yyyy-mm-dd')";
        }
        if (organizationRosterData.getMinSalary() != null && !organizationRosterData.getMinSalary().equals("")) {
            query += unionWordFor(query) + "salary >= " + organizationRosterData.getMinSalary();
        }
        if (organizationRosterData.getMaxSalary() != null && !organizationRosterData.getMaxSalary().equals("")) {
            query += unionWordFor(query) + "salary <= " + organizationRosterData.getMaxSalary();
        }
        if (organizationRosterData.getMinAge() != null && !organizationRosterData.getMinAge().equals("")) {
            query += unionWordFor(query) + "age >= " + organizationRosterData.getMinAge();
        }
        if (organizationRosterData.getMaxAge() != null && !organizationRosterData.getMaxAge().equals("")) {
            query += unionWordFor(query) + "salary <= " + organizationRosterData.getMaxAge();
        }
        if (query.endsWith(" where")) {
            query = query.substring(0, query.indexOf(" where"));
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        ArrayList<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            columnNames.add(resultSet.getMetaData().getColumnName(i));
        }
        queryResult.add(columnNames);
        while (resultSet.next()) {
            ArrayList<String> row = new ArrayList<>();
            for (int i = 1; i < 8; i++) {
                row.add(resultSet.getString(i));
            }
            queryResult.add(row);
        }

        ArrayList<ArrayList<String>> readableQueryResult = dataHandlerService.getReadableDataFrom(queryResult, TableType.EMPLOYEES);
        return new ArrayList<>(readableQueryResult.subList(1, readableQueryResult.size()));
    }
}

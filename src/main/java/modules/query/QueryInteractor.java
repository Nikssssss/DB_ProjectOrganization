package modules.query;

import modules.queries.enums.QueryType;
import modules.query.entities.OrganizationRosterData;
import services.DataTransformer;
import services.QueriesExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryInteractor {
    private QueryType queryType;

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public ArrayList<ArrayList<String>> getComboBoxData() throws SQLException {
        ArrayList<ArrayList<String>> comboBoxData = new ArrayList<>();
        switch (queryType) {
            case ORGANIZATION_ROSTER: {
                ArrayList<String> professions = QueriesExecutor.getAllProfessionNames();
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
            query += " profession_id = " + DataTransformer.getDatabaseProfessionIdBy(organizationRosterData.getProfession());
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

        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

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
        resultSet.close();

        ArrayList<ArrayList<String>> readableQueryResult = DataTransformer.getReadableColumnsAndRowsForEmployees(queryResult);
        List<ArrayList<String>> readableQueryRows = readableQueryResult.subList(1, readableQueryResult.size());
        return new ArrayList<>(readableQueryRows);
    }
}

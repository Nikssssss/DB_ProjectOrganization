package modules.query;

import modules.queries.enums.QueryType;
import modules.query.entities.OrganizationRosterData;
import services.DataTransformer;
import services.QueriesExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                break;
            }
            case PROJECTS_OF_CONTRACT: {
                ArrayList<String> contracts = QueriesExecutor.getAllContracts();
                comboBoxData.add(contracts);
                break;
            }
            case EQUIPMENT_OF_PROJECT:
            case EMPLOYEES_OF_PROJECT:
            case CONTRACT_OF_PROJECT: {
                ArrayList<String> projects = QueriesExecutor.getAllProjects();
                comboBoxData.add(projects);
                break;
            }
            default: return null;
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
            case CONTRACTS_LIST: {
                return this.getContractsListColumns();
            }
            case PROJECTS_LIST: {
                return this.getProjectsListColumns();
            }
            case EQUIPMENT_PROJECTS: {
                return this.getEquipmentProjectsColumns();
            }
            case PROJECTS_OF_CONTRACT: {
                return this.getProjectsOfContractColumns();
            }
            case CONTRACT_OF_PROJECT: {
                return this.getContractOfProjectColumns();
            }
            case EQUIPMENT_OF_PROJECT: {
                return this.getEquipmentOfProjectColumns();
            }
            case EMPLOYEES_OF_PROJECT: {
                return this.getEmployeesOfProjectColumns();
            }
            case DEPARTMENT_MANAGERS: {
                return this.getDepartmentManagersColumns();
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
            case CONTRACTS_LIST: {
                return this.executeContractsListQuery(queryParameters);
            }
            case PROJECTS_LIST: {
                return this.executeProjectsListQuery(queryParameters);
            }
            case EQUIPMENT_PROJECTS: {
                return this.executeEquipmentProjectsQuery(queryParameters);
            }
            case PROJECTS_OF_CONTRACT: {
                return this.executeProjectsOfContractQuery(queryParameters);
            }
            case CONTRACT_OF_PROJECT: {
                return this.executeContractOfProjectQuery(queryParameters);
            }
            case EQUIPMENT_OF_PROJECT: {
                return this.executeEquipmentOfProjectQuery(queryParameters);
            }
            case EMPLOYEES_OF_PROJECT: {
                return this.executeEmployeesOfProjectQuery(queryParameters);
            }
            case DEPARTMENT_MANAGERS: {
                return this.executeDepartmentManagersQuery(queryParameters);
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

    private ArrayList<String> getContractsListColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Договор");
        organizationRosterColumns.add("Дата начала");
        organizationRosterColumns.add("Дата окончания");
        return organizationRosterColumns;
    }

    private ArrayList<String> getProjectsListColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Проект");
        organizationRosterColumns.add("Дата начала");
        organizationRosterColumns.add("Дата окончания");
        return organizationRosterColumns;
    }

    private ArrayList<String> getEquipmentProjectsColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Оборудование");
        organizationRosterColumns.add("Проект");
        organizationRosterColumns.add("Дата начала");
        organizationRosterColumns.add("Дата окончания");
        return organizationRosterColumns;
    }

    private ArrayList<String> getProjectsOfContractColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Договор");
        organizationRosterColumns.add("Проект");
        return organizationRosterColumns;
    }

    private ArrayList<String> getContractOfProjectColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Проект");
        organizationRosterColumns.add("Договор");
        return organizationRosterColumns;
    }

    private ArrayList<String> getEquipmentOfProjectColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Проект");
        organizationRosterColumns.add("Оборудование");
        return organizationRosterColumns;
    }

    private ArrayList<String> getEmployeesOfProjectColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Проект");
        organizationRosterColumns.add("Сотрудник");
        return organizationRosterColumns;
    }

    private ArrayList<String> getDepartmentManagersColumns() {
        ArrayList<String> organizationRosterColumns = new ArrayList<>();
        organizationRosterColumns.add("Отдел");
        organizationRosterColumns.add("Руководитель");
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

    private ArrayList<ArrayList<String>> executeContractsListQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select contract_name, start_date, finish_date from contracts where";
        if (!queryParameters.get(0).equals("")) {
            query += unionWordFor(query) + "finish_date >= to_date('" + queryParameters.get(0) + "', 'yyyy-mm-dd')";
        }
        if (!queryParameters.get(1).equals("")) {
            query += unionWordFor(query) + "finish_date <= to_date('" + queryParameters.get(1) + "', 'yyyy-mm-dd')";
        }
        if (query.endsWith(" where")) {
            query = query.substring(0, query.indexOf(" where"));
        }

        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            String startDate = resultSet.getString(2).substring(0, 10);
            String finishDate = resultSet.getString(3).substring(0, 10);
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    startDate,
                    finishDate)));
        }
        resultSet.close();

        return queryResult;
    }

    private ArrayList<ArrayList<String>> executeProjectsListQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select project_name, start_date, finish_date from projects where";
        if (!queryParameters.get(0).equals("")) {
            query += unionWordFor(query) + "finish_date >= to_date('" + queryParameters.get(0) + "', 'yyyy-mm-dd')";
        }
        if (!queryParameters.get(1).equals("")) {
            query += unionWordFor(query) + "finish_date <= to_date('" + queryParameters.get(1) + "', 'yyyy-mm-dd')";
        }
        if (query.endsWith(" where")) {
            query = query.substring(0, query.indexOf(" where"));
        }

        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            String startDate = resultSet.getString(2).substring(0, 10);
            String finishDate = resultSet.getString(3).substring(0, 10);
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    startDate,
                    finishDate)));
        }
        resultSet.close();

        return queryResult;
    }

    private ArrayList<ArrayList<String>> executeEquipmentProjectsQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select equipment_name, project_name, start_date, finish_date from equipment_projects " +
                "inner join projects using(project_id) " +
                "inner join equipment using(equipment_id) " +
                "where";
        if (!queryParameters.get(0).equals("")) {
            query += unionWordFor(query) + "finish_date >= to_date('" + queryParameters.get(0) + "', 'yyyy-mm-dd')";
        }
        if (!queryParameters.get(1).equals("")) {
            query += unionWordFor(query) + "finish_date <= to_date('" + queryParameters.get(1) + "', 'yyyy-mm-dd')";
        }
        if (query.endsWith(" where")) {
            query = query.substring(0, query.indexOf(" where"));
        }

        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            String startDate = resultSet.getString(3).substring(0, 10);
            String finishDate = resultSet.getString(4).substring(0, 10);
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    resultSet.getString(2),
                    startDate,
                    finishDate)));
        }
        resultSet.close();

        return queryResult;
    }

    private ArrayList<ArrayList<String>> executeProjectsOfContractQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select contract_name, project_name from projects_contracts " +
                "inner join contracts using(contract_id) " +
                "inner join projects using(project_id) " +
                "where contract_name = '" + queryParameters.get(0) + "'";
        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    resultSet.getString(2))));
        }
        resultSet.close();

        return queryResult;
    }

    private ArrayList<ArrayList<String>> executeContractOfProjectQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select project_name, contract_name from projects_contracts " +
                "inner join contracts using(contract_id) " +
                "inner join projects using(project_id) " +
                "where project_name = '" + queryParameters.get(0) + "'";
        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    resultSet.getString(2))));
        }
        resultSet.close();

        return queryResult;
    }

    private ArrayList<ArrayList<String>> executeEquipmentOfProjectQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select project_name, equipment_name from equipment_projects " +
                "inner join equipment using(equipment_id) " +
                "inner join projects using(project_id) " +
                "where project_name = '" + queryParameters.get(0) + "'";
        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    resultSet.getString(2))));
        }
        resultSet.close();

        return queryResult;
    }

    private ArrayList<ArrayList<String>> executeEmployeesOfProjectQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select project_name, employee_id from projects_employees " +
                "inner join employees using(employee_id) " +
                "inner join projects using(project_id) " +
                "where project_name = '" + queryParameters.get(0) + "'";
        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            String employee = QueriesExecutor.getManagerInfoBy(resultSet.getString(2));
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    employee)));
        }
        resultSet.close();

        return queryResult;
    }

    private ArrayList<ArrayList<String>> executeDepartmentManagersQuery(ArrayList<String> queryParameters) throws SQLException {
        String query = "select department_name, manager_id from departments";
        ResultSet resultSet = QueriesExecutor.executeBusinessQuery(query);

        ArrayList<ArrayList<String>> queryResult = new ArrayList<>();
        while (resultSet.next()) {
            String employee = QueriesExecutor.getManagerInfoBy(resultSet.getString(2));
            queryResult.add(new ArrayList<>(Arrays.asList(resultSet.getString(1),
                    employee)));
        }
        resultSet.close();

        return queryResult;
    }
}

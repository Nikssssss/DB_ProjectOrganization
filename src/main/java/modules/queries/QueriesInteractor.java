package modules.queries;

import common.CurrentUserRole;
import modules.queries.enums.QueryType;

import java.util.ArrayList;

public class QueriesInteractor {
    public ArrayList<String> getAllQueries() {
        ArrayList<QueryType> queryTypes = new ArrayList<>();
        switch (CurrentUserRole.getUserRole()) {
            case MANAGER: {
                queryTypes.add(QueryType.CONTRACTS_LIST);
                queryTypes.add(QueryType.PROJECTS_LIST);
                queryTypes.add(QueryType.PROJECTS_OF_CONTRACT);
                queryTypes.add(QueryType.CONTRACT_OF_PROJECT);
                queryTypes.add(QueryType.EQUIPMENT_OF_PROJECT);
                queryTypes.add(QueryType.EMPLOYEES_OF_PROJECT);
                queryTypes.add(QueryType.EQUIPMENT_PROJECTS);
                break;
            }
            case HR: {
                queryTypes.add(QueryType.ORGANIZATION_ROSTER);
                queryTypes.add(QueryType.DEPARTMENT_MANAGERS);
                break;
            }
            case DIRECTOR: {
                queryTypes.add(QueryType.ORGANIZATION_ROSTER);
                queryTypes.add(QueryType.DEPARTMENT_MANAGERS);
                queryTypes.add(QueryType.CONTRACTS_LIST);
                queryTypes.add(QueryType.PROJECTS_LIST);
                queryTypes.add(QueryType.PROJECTS_OF_CONTRACT);
                queryTypes.add(QueryType.CONTRACT_OF_PROJECT);
                queryTypes.add(QueryType.EQUIPMENT_OF_PROJECT);
                queryTypes.add(QueryType.EMPLOYEES_OF_PROJECT);
                queryTypes.add(QueryType.EQUIPMENT_PROJECTS);
                break;
            }
        }
        ArrayList<String> queries = new ArrayList<>();
        for (QueryType queryType: queryTypes) {
            queries.add(queryType.getQueryDescription());
        }
        return queries;
    }
}

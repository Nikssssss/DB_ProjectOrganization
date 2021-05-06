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
                queryTypes.add(QueryType.COST_OF_PROJECTS);
                queryTypes.add(QueryType.SUBPROJECTS_LIST);
                queryTypes.add(QueryType.SUBPROJECTS_COST);
                queryTypes.add(QueryType.PROJECT_ROSTER_COUNT);
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
                queryTypes.add(QueryType.COST_OF_PROJECTS);
                queryTypes.add(QueryType.SUBPROJECTS_LIST);
                queryTypes.add(QueryType.SUBPROJECTS_COST);
                queryTypes.add(QueryType.PROJECT_ROSTER_COUNT);
                queryTypes.add(QueryType.EQUIPMENT_EFFICIENCY);
                queryTypes.add(QueryType.CONTRACT_EFFICIENCY);
                queryTypes.add(QueryType.PROJECT_EFFICIENCY);
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

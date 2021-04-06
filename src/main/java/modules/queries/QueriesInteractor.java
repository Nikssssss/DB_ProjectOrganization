package modules.queries;

import modules.queries.enums.QueryType;

import java.util.ArrayList;

public class QueriesInteractor {

    public ArrayList<String> getAllQueries() {
        ArrayList<String> queries = new ArrayList<>();
        for (QueryType queryType: QueryType.values()) {
            queries.add(queryType.getQueryDescription());
        }
        return queries;
    }

}

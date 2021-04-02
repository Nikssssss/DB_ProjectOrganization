package modules.queries;

import modules.queries.enums.QueryType;

import java.sql.Connection;
import java.util.ArrayList;

public class QueriesInteractor {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public ArrayList<String> getAllQueries() {
        ArrayList<String> queries = new ArrayList<>();
        for (QueryType queryType: QueryType.values()) {
            queries.add(queryType.getQueryDescription());
        }
        return queries;
    }
}

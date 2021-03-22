package modules.tables.services;

import java.sql.Connection;

public class DataHandlerService {
    private Connection connection;

    public DataHandlerService(Connection connection) {
        this.connection = connection;
    }

//    public ArrayList<ArrayList<String>> getReadableDataFrom(ArrayList<ArrayList<String>> data, TableType tableType) {
//
//    }
}

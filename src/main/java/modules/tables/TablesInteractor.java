package modules.tables;

import java.sql.Connection;

public class TablesInteractor {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

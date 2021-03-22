package modules.main;

import java.sql.Connection;

public class MainInteractor {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}

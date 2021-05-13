package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class DatabaseConnection {
    private static volatile Connection connection;

    public static void createConnection(String url, String user, String password) throws ClassNotFoundException, SQLException {
        if (connection != null) {
            connection.close();
        }
        synchronized (DatabaseConnection.class) {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.setLoginTimeout(2);
            Locale.setDefault(Locale.ENGLISH);
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
        }
    }

    private DatabaseConnection() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }

    public static Connection getConnection() {
        return connection;
    }
}

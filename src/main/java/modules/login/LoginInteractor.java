package modules.login;

import common.DatabaseConnection;
import services.DataUpdater;
import services.QueriesExecutor;

import java.sql.SQLException;

public class LoginInteractor {

    public void connect(String ip, String port, String user, String password) throws ClassNotFoundException, SQLException {
        String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":XE";
        DatabaseConnection.createConnection(url, user, password);
        QueriesExecutor.setConnection();
        DataUpdater.setConnection();
    }

}

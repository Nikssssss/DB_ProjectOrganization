package modules.login;

import common.DatabaseConnection;
import modules.roles.enums.UserRole;
import services.DataUpdater;
import services.QueriesExecutor;

import java.sql.SQLException;
import java.util.ArrayList;

public class LoginInteractor {
    public void connect(String ip, String port, String user, String password) throws ClassNotFoundException, SQLException {
        String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":XE";
        DatabaseConnection.createConnection(url, user, password);
        QueriesExecutor.setConnection();
        DataUpdater.setConnection();
    }

    public UserRole getUserRole() throws SQLException {
        ArrayList<String> roles = QueriesExecutor.getUserRole();
        for (String role: roles) {
            System.out.println(role);
        }
        if (roles.contains("manager_ng") && roles.contains("hr_ng") && roles.contains("director_ng")) {
            return UserRole.ADMIN;
        } else if (roles.contains("manager_ng")) {
            return UserRole.MANAGER;
        } else if (roles.contains("hr_ng")) {
            return UserRole.HR;
        } else if (roles.contains("director_ng")) {
            return UserRole.DIRECTOR;
        } else {
            return UserRole.ADMIN;
        }
    }
}

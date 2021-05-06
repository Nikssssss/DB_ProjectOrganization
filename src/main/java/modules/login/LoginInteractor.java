package modules.login;

import common.DatabaseConnection;
import modules.roles.enums.UserRole;
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

    public UserRole getUserRoleBy(String login, String password) throws SQLException {
        String userProfession = QueriesExecutor.getEmployeeProfession(login, password);
        if (userProfession == null) {
            return null;
        } else if (userProfession.equals("Конструктор") || userProfession.equals("Инженер")) {
            return UserRole.MANAGER;
        } else if (userProfession.equals("Директор")) {
            return UserRole.DIRECTOR;
        } else if (userProfession.equals("Менеджер") || userProfession.equals("Бухгалтер")) {
            return UserRole.HR;
        } else if (userProfession.equals("Админ")) {
            return UserRole.ADMIN;
        } else {
            return null;
        }
    }
}

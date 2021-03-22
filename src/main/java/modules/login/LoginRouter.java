package modules.login;

import application.MainWindow;
import modules.main.MainAssembly;
import modules.main.MainView;

import java.sql.Connection;

public class LoginRouter {
    private MainWindow mainWindow;

    public LoginRouter(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showMainScene(Connection connection) {
        MainView mainView = MainAssembly.assemble(mainWindow, connection);
        mainView.didLoad();
        mainWindow.showPanel(mainView.getMainPanel());
    }
}

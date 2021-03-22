package modules.tables;

import application.MainWindow;
import modules.main.MainAssembly;
import modules.main.MainView;

import java.sql.Connection;

public class TablesRouter {
    private MainWindow mainWindow;

    public TablesRouter(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showMainScene(Connection connection) {
        MainView mainView = MainAssembly.assemble(mainWindow, connection);
        mainView.didLoad();
        mainWindow.showPanel(mainView.getMainPanel());
    }
}

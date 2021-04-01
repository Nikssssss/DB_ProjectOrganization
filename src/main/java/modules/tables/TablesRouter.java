package modules.tables;

import application.MainWindow;
import modules.adding.AddingAssembly;
import modules.adding.AddingView;
import modules.main.MainAssembly;
import modules.main.MainView;
import modules.tables.enums.TableType;

import java.sql.Connection;
import java.sql.ResultSet;

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

    public void showAddingScene(Connection connection, ResultSet resultSet, TableType tableType) {
        AddingView addingView = AddingAssembly.assemble(mainWindow, connection, resultSet, tableType);
        addingView.didLoad();
        mainWindow.showPanel(addingView.getAddingPanel());
    }
}

package modules.adding;

import application.MainWindow;
import modules.main.MainAssembly;
import modules.main.MainView;
import modules.tables.TablesAssembly;
import modules.tables.TablesView;

import java.sql.Connection;

public class AddingRouter {
    private MainWindow mainWindow;

    public AddingRouter(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showTablesScene(Connection connection) {
        TablesView tablesView = TablesAssembly.assemble(mainWindow, connection);
        tablesView.didLoad();
        mainWindow.showPanel(tablesView.getTablePanel());
    }
}

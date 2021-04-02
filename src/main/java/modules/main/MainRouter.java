package modules.main;

import application.MainWindow;
import modules.queries.QueriesAssembly;
import modules.queries.QueriesView;
import modules.tables.TablesAssembly;
import modules.tables.TablesView;

import java.sql.Connection;

public class MainRouter {
    private MainWindow mainWindow;

    public MainRouter(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showTableScene(Connection connection) {
        TablesView tablesView = TablesAssembly.assemble(mainWindow, connection);
        tablesView.didLoad();
        mainWindow.showPanel(tablesView.getTablePanel());
    }

    public void showQueriesScene(Connection connection) {
        QueriesView queriesView = QueriesAssembly.assemble(mainWindow, connection);
        queriesView.didLoad();
        mainWindow.showPanel(queriesView.getQueriesPanel());
    }
}

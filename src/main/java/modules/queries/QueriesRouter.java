package modules.queries;

import application.MainWindow;
import modules.main.MainAssembly;
import modules.main.MainView;
import modules.queries.enums.QueryType;
import modules.query.QueryAssembly;
import modules.query.QueryView;
import modules.tables.TablesAssembly;
import modules.tables.TablesView;

import java.sql.Connection;

public class QueriesRouter {
    private final MainWindow mainWindow;

    public QueriesRouter(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showMenuScene(Connection connection) {
        MainView mainView = MainAssembly.assemble(mainWindow, connection);
        mainView.didLoad();
        mainWindow.showPanel(mainView.getMainPanel());
    }

    public void showQueryScene(Connection connection, QueryType queryType) {
        QueryView queryView = QueryAssembly.assemble(mainWindow, connection, queryType);
        queryView.didLoad();
        mainWindow.showPanel(queryView.getQueryPanel());
    }
}

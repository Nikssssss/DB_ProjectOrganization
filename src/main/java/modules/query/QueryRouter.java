package modules.query;

import application.MainWindow;
import modules.main.MainAssembly;
import modules.main.MainView;
import modules.queries.QueriesAssembly;
import modules.queries.QueriesView;
import modules.queries.enums.QueryType;

import java.sql.Connection;

public class QueryRouter {
    private final MainWindow mainWindow;

    public QueryRouter(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showQueriesScene(Connection connection) {
        QueriesView queriesView = QueriesAssembly.assemble(mainWindow, connection);
        queriesView.didLoad();
        mainWindow.showPanel(queriesView.getQueriesPanel());
    }
}

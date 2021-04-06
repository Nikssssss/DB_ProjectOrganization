package modules.main;

import common.MainWindow;
import modules.queries.QueriesAssembly;
import modules.queries.QueriesView;
import modules.tables.TablesAssembly;
import modules.tables.TablesView;

public class MainRouter {

    public void showTableScene() {
        TablesView tablesView = TablesAssembly.assemble();
        tablesView.didLoad();
        MainWindow.getMainWindow().showPanel(tablesView.getTablePanel());
    }

    public void showQueriesScene() {
        QueriesView queriesView = QueriesAssembly.assemble();
        queriesView.didLoad();
        MainWindow.getMainWindow().showPanel(queriesView.getQueriesPanel());
    }

}

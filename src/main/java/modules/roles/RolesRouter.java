package modules.roles;

import common.MainWindow;
import modules.login.LoginAssembly;
import modules.login.LoginView;
import modules.queries.QueriesAssembly;
import modules.queries.QueriesView;
import modules.tables.TablesAssembly;
import modules.tables.TablesView;

public class RolesRouter {
    public void showLoginScene() {
        LoginView loginView = LoginAssembly.assemble();
        loginView.didLoad();
        MainWindow.getMainWindow().showPanel(loginView.getLoginPanel());
    }

    public void showTablesScene() {
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

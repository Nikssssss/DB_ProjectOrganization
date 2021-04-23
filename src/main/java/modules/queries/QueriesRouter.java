package modules.queries;

import common.MainWindow;
import modules.queries.enums.QueryType;
import modules.query.QueryAssembly;
import modules.query.QueryView;
import modules.roles.RolesAssembly;
import modules.roles.views.UserRoleView;

public class QueriesRouter {
    public void showMenuScene() {
        UserRoleView userRoleView = RolesAssembly.assemble();
        userRoleView.didLoad();
        MainWindow.getMainWindow().showPanel(userRoleView.getPanel());
    }

    public void showQueryScene(QueryType queryType) {
        QueryView queryView = QueryAssembly.assemble(queryType);
        queryView.didLoad();
        MainWindow.getMainWindow().showPanel(queryView.getQueryPanel());
    }
}

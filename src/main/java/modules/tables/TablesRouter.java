package modules.tables;

import common.MainWindow;
import modules.adding.AddingAssembly;
import modules.adding.AddingView;
import modules.roles.RolesAssembly;
import modules.roles.views.UserRoleView;
import modules.tables.enums.TableType;

import java.sql.ResultSet;

public class TablesRouter {

    public void showMainScene() {
        UserRoleView userRoleView = RolesAssembly.assemble();
        userRoleView.didLoad();
        MainWindow.getMainWindow().showPanel(userRoleView.getPanel());
    }

    public void showAddingScene(ResultSet resultSet, TableType tableType) {
        AddingView addingView = AddingAssembly.assemble(resultSet, tableType);
        addingView.didLoad();
        MainWindow.getMainWindow().showPanel(addingView.getAddingPanel());
    }
}

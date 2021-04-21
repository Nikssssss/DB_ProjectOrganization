package modules.login;

import common.MainWindow;
import modules.roles.RolesAssembly;
import modules.roles.views.UserRoleView;

public class LoginRouter {
    public void showUserRoleScene() {
        UserRoleView userRoleView = RolesAssembly.assemble();
        userRoleView.didLoad();
        MainWindow.getMainWindow().showPanel(userRoleView.getPanel());
    }
}

package application;

import common.MainWindow;
import modules.login.LoginAssembly;
import modules.login.LoginView;

public class ProjectOrganization {
    public void start(){
        MainWindow.createWindow();
        LoginView loginView = LoginAssembly.assemble();
        loginView.didLoad();
        MainWindow.getMainWindow().showPanel(loginView.getLoginPanel());
    }
}

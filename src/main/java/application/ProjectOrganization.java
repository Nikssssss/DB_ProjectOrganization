package application;

import modules.login.LoginAssembly;
import modules.login.LoginView;

public class ProjectOrganization {
    public void start(){
        MainWindow mainWindow = new MainWindow();
        LoginView loginView = LoginAssembly.assemble(mainWindow);
        loginView.didLoad();
        mainWindow.showPanel(loginView.getLoginPanel());
    }
}

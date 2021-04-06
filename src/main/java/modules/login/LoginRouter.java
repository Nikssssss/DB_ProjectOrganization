package modules.login;

import common.MainWindow;
import modules.main.MainAssembly;
import modules.main.MainView;

public class LoginRouter {

    public void showMainScene() {
        MainView mainView = MainAssembly.assemble();
        mainView.didLoad();
        MainWindow.getMainWindow().showPanel(mainView.getMainPanel());
    }

}

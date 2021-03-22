package modules.login;

import application.MainWindow;

public class LoginAssembly {
    public static LoginView assemble(MainWindow mainWindow) {
        LoginView view = new LoginView();
        LoginPresenter presenter = new LoginPresenter(view);
        LoginInteractor interactor = new LoginInteractor();
        LoginRouter router = new LoginRouter(mainWindow);

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        return view;
    }
}

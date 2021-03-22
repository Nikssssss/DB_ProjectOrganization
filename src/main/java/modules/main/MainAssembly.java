package modules.main;

import application.MainWindow;

import java.sql.Connection;

public class MainAssembly {
    public static MainView assemble(MainWindow mainWindow, Connection connection) {
        MainView view = new MainView();
        MainPresenter presenter = new MainPresenter(view);
        MainInteractor interactor = new MainInteractor();
        MainRouter router = new MainRouter(mainWindow);

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setConnection(connection);

        return view;
    }
}

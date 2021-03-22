package modules.tables;

import application.MainWindow;

import java.sql.Connection;

public class TablesAssembly {
    public static TablesView assemble(MainWindow mainWindow, Connection connection) {
        TablesView view = new TablesView();
        TablesPresenter presenter = new TablesPresenter(view);
        TablesInteractor interactor = new TablesInteractor();
        TablesRouter router = new TablesRouter(mainWindow);

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setConnection(connection);

        return view;
    }
}

package modules.queries;

import application.MainWindow;

import java.sql.Connection;

public class QueriesAssembly {
    public static QueriesView assemble(MainWindow mainWindow, Connection connection) {
        QueriesView view = new QueriesView();
        QueriesPresenter presenter = new QueriesPresenter(view);
        QueriesInteractor interactor = new QueriesInteractor();
        QueriesRouter router = new QueriesRouter(mainWindow);

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setConnection(connection);

        return view;
    }
}

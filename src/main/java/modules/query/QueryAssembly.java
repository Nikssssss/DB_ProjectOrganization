package modules.query;

import application.MainWindow;
import modules.queries.enums.QueryType;

import java.sql.Connection;

public class QueryAssembly {
    public static QueryView assemble(MainWindow mainWindow, Connection connection, QueryType queryType) {
        QueryView view = new QueryView();
        QueryPresenter presenter = new QueryPresenter(view);
        QueryInteractor interactor = new QueryInteractor();
        QueryRouter router = new QueryRouter(mainWindow);

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setConnection(connection);
        interactor.setQueryType(queryType);

        return view;
    }
}

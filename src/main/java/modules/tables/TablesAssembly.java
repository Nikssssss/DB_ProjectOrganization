package modules.tables;

import common.DatabaseConnection;

public class TablesAssembly {
    public static TablesView assemble() {
        TablesView view = new TablesView();
        TablesPresenter presenter = new TablesPresenter(view);
        TablesInteractor interactor = new TablesInteractor();
        TablesRouter router = new TablesRouter();

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setConnection(DatabaseConnection.getConnection());

        return view;
    }
}

package modules.main;

import common.DatabaseConnection;

public class MainAssembly {
    public static MainView assemble() {
        MainView view = new MainView();
        MainPresenter presenter = new MainPresenter(view);
        MainInteractor interactor = new MainInteractor();
        MainRouter router = new MainRouter();

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        interactor.setConnection(DatabaseConnection.getConnection());

        return view;
    }
}

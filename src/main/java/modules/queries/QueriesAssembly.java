package modules.queries;

public class QueriesAssembly {
    public static QueriesView assemble() {
        QueriesView view = new QueriesView();
        QueriesPresenter presenter = new QueriesPresenter(view);
        QueriesInteractor interactor = new QueriesInteractor();
        QueriesRouter router = new QueriesRouter();

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        return view;
    }
}

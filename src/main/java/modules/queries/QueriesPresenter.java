package modules.queries;

import modules.queries.enums.QueryType;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class QueriesPresenter {
    private WeakReference<QueriesView> view;
    private QueriesInteractor interactor;
    private QueriesRouter router;

    public QueriesPresenter(QueriesView view) {
        this.view = new WeakReference<>(view);
    }

    public void setInteractor(QueriesInteractor interactor) {
        this.interactor = interactor;
    }

    public void setRouter(QueriesRouter router) {
        this.router = router;
    }

    public void viewDidLoad() {
        Objects.requireNonNull(this.view.get()).configureView();
        ArrayList<String> queries = interactor.getAllQueries();
        Objects.requireNonNull(this.view.get()).setQueriesTableData(queries);
    }

    public void backToMenuButtonPressed() {
        router.showMenuScene();
    }

    public void rowDidSelect(int row) {
        router.showQueryScene(QueryType.valueOfRaw(row));
    }

}

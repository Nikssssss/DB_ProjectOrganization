package modules.query;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class QueryPresenter {
    private WeakReference<QueryView> view;
    private QueryInteractor interactor;
    private QueryRouter router;

    public QueryPresenter(QueryView view) {
        this.view = new WeakReference<>(view);
    }

    public void setInteractor(QueryInteractor interactor) {
        this.interactor = interactor;
    }

    public void setRouter(QueryRouter router) {
        this.router = router;
    }

    public void viewDidLoad() {
        try {
            ArrayList<ArrayList<String>> comboBoxData = interactor.getComboBoxData();
            Objects.requireNonNull(this.view.get()).configureView(interactor.getQueryType(), comboBoxData);
            ArrayList<String> columnNames = interactor.getAllColumns();
            Objects.requireNonNull(this.view.get()).setQueryResultStructure(columnNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backToQueriesButtonPressed() {
        router.showQueriesScene();
    }

    public void executeQueryButtonPressed() {
        ArrayList<String> queryParameters = Objects.requireNonNull(view.get()).getAllParameters();
        try {
            ArrayList<ArrayList<String>> queryResult = interactor.executeQuery(queryParameters);
            Objects.requireNonNull(view.get()).setQueryResultData(queryResult);
        } catch (SQLException e) {
            Objects.requireNonNull(view.get()).setErrorMessage("Введите корректные значения");
            e.printStackTrace();
        }
    }
}

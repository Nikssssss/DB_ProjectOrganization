package modules.main;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.Objects;

public class MainPresenter {
    private WeakReference<MainView> view;
    private MainInteractor interactor;
    private MainRouter router;

    public MainPresenter(MainView view) {
        this.view = new WeakReference<>(view);
    }

    public void setInteractor(MainInteractor interactor) {
        this.interactor = interactor;
    }

    public void setRouter(MainRouter router) {
        this.router = router;
    }

    public void viewDidLoad() {
        Objects.requireNonNull(this.view.get()).configureView();
    }

    public void tablesButtonPressed() {
        router.showTableScene();
    }

    public void queriesButtonPressed() {
        router.showQueriesScene();
    }

    public void createDatabaseButtonPressed() {
        try {
            Objects.requireNonNull(view.get()).disableAllButtons();
            interactor.initializeDatabase();
            Objects.requireNonNull(view.get()).enableAllButtons();
        } catch (SQLException e) {
            if (e.getErrorCode() == 17081) {
                Objects.requireNonNull(view.get()).setErrorMessage("База данных уже проинициализирована");
            } else {
                Objects.requireNonNull(view.get()).setErrorMessage(e.getSQLState());
            }
            Objects.requireNonNull(view.get()).enableAllButtons();
        }
    }

    public void clearDatabaseButtonPressed() {
        try {
            Objects.requireNonNull(view.get()).disableAllButtons();
            interactor.clearDatabase();
            Objects.requireNonNull(view.get()).enableAllButtons();
        } catch (SQLException e) {
            if (e.getErrorCode() == 17081) {
                Objects.requireNonNull(view.get()).setErrorMessage("База данных уже очищена");
            } else {
                Objects.requireNonNull(view.get()).setErrorMessage(e.getSQLState());
            }
            Objects.requireNonNull(view.get()).enableAllButtons();
        }
    }
}

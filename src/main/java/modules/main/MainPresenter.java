package modules.main;

import java.lang.ref.SoftReference;
import java.util.Objects;

public class MainPresenter {
    private SoftReference<MainView> view;
    private MainInteractor interactor;
    private MainRouter router;

    public MainPresenter(MainView view) {
        this.view = new SoftReference<>(view);
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
        router.showTableScene(interactor.getConnection());
    }

    public void queriesButtonPressed() {
        router.showQueriesScene(interactor.getConnection());
    }
}

package modules.tables;

import java.lang.ref.SoftReference;
import java.util.Objects;

public class TablesPresenter {
    private SoftReference<TablesView> view;
    private TablesInteractor interactor;
    private TablesRouter router;

    public TablesPresenter(TablesView view) {
        this.view = new SoftReference<>(view);
    }

    public void setInteractor(TablesInteractor interactor) {
        this.interactor = interactor;
    }

    public void setRouter(TablesRouter router) {
        this.router = router;
    }

    public void viewDidLoad() {
        Objects.requireNonNull(this.view.get()).configureView();
    }

    public void backButtonPressed() {
        this.router.showMainScene(this.interactor.getConnection());
    }
}

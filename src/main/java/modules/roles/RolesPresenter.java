package modules.roles;

import modules.roles.views.UserRoleView;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.Objects;

public class RolesPresenter {
    private final WeakReference<UserRoleView> view;
    private final RolesRouter router;
    private final RolesInteractor interactor;

    public RolesPresenter(UserRoleView view, RolesRouter router, RolesInteractor interactor) {
        this.view = new WeakReference<>(view);
        this.router = router;
        this.interactor = interactor;
    }

    public void configureView() {
        Objects.requireNonNull(view.get()).configureView();
    }

    public void backButtonPressed() {
        router.showLoginScene();
    }

    public void tablesButtonPressed() {
        try {
            interactor.checkDatabaseCreation();
            router.showTablesScene();
        } catch (SQLException e) {
            e.printStackTrace();
            Objects.requireNonNull(view.get()).setErrorMessage("База данных очищена.\n" +
                    "Пожалуйста, попросите Админа нажать кнопку \"Создать БД\" для инициализации базы данных");
        }
    }

    public void queriesButtonPressed() {
        try {
            interactor.checkDatabaseCreation();
            router.showQueriesScene();
        } catch (SQLException e) {
            e.printStackTrace();
            Objects.requireNonNull(view.get()).setErrorMessage("База данных очищена.\n" +
                    "Пожалуйста, попросите Админа нажать кнопку \"Создать БД\" для инициализации базы данных");
        }
    }

    public void createDatabaseButtonPressed() {
        try {
            interactor.initializeDatabase();
        } catch (SQLException e) {
            if (e.getErrorCode() == 17081) {
                e.printStackTrace();
                Objects.requireNonNull(view.get()).setErrorMessage("База данных уже проинициализирована");
            } else {
                Objects.requireNonNull(view.get()).setErrorMessage(e.getMessage());
            }
        }
    }

    public void clearDatabaseButtonPressed() {
        try {
            interactor.clearDatabase();
        } catch (SQLException e) {
            if (e.getErrorCode() == 17081) {
                Objects.requireNonNull(view.get()).setErrorMessage("База данных уже очищена");
            } else {
                Objects.requireNonNull(view.get()).setErrorMessage(e.getSQLState());
            }
        }
    }
}

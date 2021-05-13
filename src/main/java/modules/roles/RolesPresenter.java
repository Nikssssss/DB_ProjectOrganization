package modules.roles;

import modules.register.RegisterView;
import modules.roles.views.UserRoleView;
import services.QueriesExecutor;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
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
        router.showTablesScene();
    }

    public void queriesButtonPressed() {
        router.showQueriesScene();
    }

    public void createDatabaseButtonPressed() {
        try {
            interactor.createDatabase();
        } catch (SQLException e) {
            if (e.getErrorCode() == 17081) {
                e.printStackTrace();
                Objects.requireNonNull(view.get()).setErrorMessage("База данных уже проинициализирована");
            } else {
                Objects.requireNonNull(view.get()).setErrorMessage(e.getMessage());
            }
        }
    }

    public void removeDatabaseButtonPressed() {
        try {
            interactor.removeDatabase();
        } catch (SQLException e) {
            if (e.getErrorCode() == 17081) {
                e.printStackTrace();
                Objects.requireNonNull(view.get()).setErrorMessage("База данных уже очищена");
            } else {
                e.printStackTrace();
                Objects.requireNonNull(view.get()).setErrorMessage(e.getSQLState());
            }
        }
    }

    public void fillDatabaseButtonPressed() {
        try {
            interactor.initializeDatabase();
        } catch (SQLException e) {
            if (e.getErrorCode() == 17081) {
                if (e.getMessage().contains("does not exist")) {
                    Objects.requireNonNull(view.get()).setErrorMessage("База данных удалена");
                } else {
                    Objects.requireNonNull(view.get()).setErrorMessage("База данных уже проинициализирована");
                }
                e.printStackTrace();
                System.out.println(e.getSQLState());
                System.out.println(e.getErrorCode());
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
                e.printStackTrace();
                Objects.requireNonNull(view.get()).setErrorMessage("База данных уже очищена");
            } else {
                e.printStackTrace();
                Objects.requireNonNull(view.get()).setErrorMessage(e.getSQLState());
            }
        }
    }

    public void addUserButtonPressed() {
        new RegisterView();
    }
}

package modules.login;

import java.lang.ref.SoftReference;
import java.sql.SQLException;
import java.util.Objects;

public class LoginPresenter {
    private SoftReference<LoginView> view;
    private LoginInteractor interactor;
    private LoginRouter router;

    public LoginPresenter(LoginView view) {
        this.view = new SoftReference<>(view);
    }

    public void setInteractor(LoginInteractor interactor) {
        this.interactor = interactor;
    }

    public void setRouter(LoginRouter router) {
        this.router = router;
    }

    public void viewDidLoad() {
        Objects.requireNonNull(this.view.get()).configureView();
    }

    public void loginButtonPressed(String ip, String port, String login, String password) {
        this.login(ip, port, login, password);
    }

    //MARK: private methods

    private void login(String ip, String port, String login, String password){
        try {
            interactor.connect(ip, port, login, password);
        } catch (ClassNotFoundException e) {
            Objects.requireNonNull(view.get()).setErrorMessage("Database is unreachable");
            return;
        } catch (SQLException ex) {
            Objects.requireNonNull(view.get()).setErrorMessage("Login failed! Please recheck entered data");
            return;
        }
        try {
            interactor.initializeDatabase();
            router.showMainScene(interactor.getConnection());
        } catch (SQLException e) {
            Objects.requireNonNull(view.get()).setErrorMessage("Error during database initializing");
            e.printStackTrace();
        }
    }
}

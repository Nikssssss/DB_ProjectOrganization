package modules.login;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.Objects;

public class LoginPresenter {
    private final WeakReference<LoginView> view;
    private LoginInteractor interactor;
    private LoginRouter router;

    public LoginPresenter(LoginView view) {
        this.view = new WeakReference<>(view);
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

    public void localTemplateButtonPressed() {
        Objects.requireNonNull(view.get()).setLocalTemplateData();
    }

    public void remoteTemplateButtonPressed() {
        Objects.requireNonNull(view.get()).setRemoteTemplateData();
    }

    //MARK: private methods

    private void login(String ip, String port, String login, String password){
        try {
            interactor.connect(ip, port, login, password);
            router.showMainScene();
        } catch (ClassNotFoundException e) {
            Objects.requireNonNull(view.get()).setErrorMessage("База данных недоступна");
        } catch (SQLException ex) {
            Objects.requireNonNull(view.get()).setErrorMessage("Ошибка авторизации! Пожалуйста, проверьте введённые данные");
        }
    }
}

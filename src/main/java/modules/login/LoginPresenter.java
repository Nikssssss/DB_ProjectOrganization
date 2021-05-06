package modules.login;

import common.CurrentUserRole;
import modules.roles.enums.UserRole;

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

    public void loginButtonPressed(String ip, String port, String login, String password, String systemLogin, String systemPassword) {
        this.login(ip, port, login, password, systemLogin, systemPassword);
    }

    public void localTemplateButtonPressed() {
        Objects.requireNonNull(view.get()).setLocalTemplateData();
    }

    public void remoteTemplateButtonPressed() {
        Objects.requireNonNull(view.get()).setRemoteTemplateData();
    }

    //MARK: private methods

    private void login(String ip, String port, String login, String password, String systemLogin, String systemPassword){
        try {
            if (Objects.requireNonNull(view.get()).hasBlankFields()) {
                Objects.requireNonNull(view.get()).setErrorMessage("Пожалуйста, заполните все поля");
                return;
            }
            interactor.connect(ip, port, login, password);
            if (systemLogin.equals("admin") && systemPassword.equals("admin")) {
                CurrentUserRole.setUserRole(UserRole.ADMIN);
                router.showUserRoleScene();
                return;
            }
            UserRole currentUserRole = interactor.getUserRoleBy(systemLogin, systemPassword);
            if (currentUserRole == null) {
                Objects.requireNonNull(view.get()).setErrorMessage("Пожалуйста, введите корректные системные логин и пароль");
                return;
            }
            CurrentUserRole.setUserRole(currentUserRole);
            router.showUserRoleScene();
        } catch (ClassNotFoundException e) {
            Objects.requireNonNull(view.get()).setErrorMessage("База данных недоступна");
        } catch (SQLException ex) {
            ex.printStackTrace();
            Objects.requireNonNull(view.get()).setErrorMessage("Ошибка авторизации! Пожалуйста, проверьте введённые данные");
        }
    }
}

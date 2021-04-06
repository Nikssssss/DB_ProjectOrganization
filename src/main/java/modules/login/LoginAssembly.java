package modules.login;

public class LoginAssembly {
    public static LoginView assemble() {
        LoginView view = new LoginView();
        LoginPresenter presenter = new LoginPresenter(view);
        LoginInteractor interactor = new LoginInteractor();
        LoginRouter router = new LoginRouter();

        view.setPresenter(presenter);
        presenter.setInteractor(interactor);
        presenter.setRouter(router);

        return view;
    }
}

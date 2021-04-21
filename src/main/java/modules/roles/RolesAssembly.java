package modules.roles;

import common.CurrentUserRole;
import modules.roles.views.*;

public class RolesAssembly {
    public static UserRoleView assemble() {
        UserRoleView view = null;
        switch (CurrentUserRole.getUserRole()) {
            case DIRECTOR:
                view = new DirectorView();
                break;
            case HR:
                view = new HRView();
                break;
            case MANAGER:
                view = new ManagerView();
                break;
            case ADMIN:
                view = new AdminView();
                break;
        }
        RolesInteractor interactor = new RolesInteractor();
        RolesRouter router = new RolesRouter();
        RolesPresenter presenter = new RolesPresenter(view, router, interactor);

        view.setPresenter(presenter);

        return view;
    }
}

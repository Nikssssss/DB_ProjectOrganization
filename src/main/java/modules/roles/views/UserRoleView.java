package modules.roles.views;

import modules.roles.RolesPresenter;

import javax.swing.*;

public interface UserRoleView {
    void configureView();
    JPanel getPanel();
    void didLoad();
    void setPresenter(RolesPresenter rolesPresenter);
    void setErrorMessage(String errorMessage);
}

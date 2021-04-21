package common;

import modules.roles.enums.UserRole;

public class CurrentUserRole {
    private static UserRole userRole;

    public static void setUserRole(UserRole userRole) {
        CurrentUserRole.userRole = userRole;
    }

    public static UserRole getUserRole() {
        return userRole;
    }
}

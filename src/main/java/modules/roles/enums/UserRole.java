package modules.roles.enums;

public enum UserRole {
    DIRECTOR("Директор"),
    HR("HR"),
    ADMIN("Админ"),
    MANAGER("Управляющий");

    private final String label;

    private UserRole(String label) {
        this.label = label;
    }

    public static UserRole valueOfLabel(String label) {
        for (UserRole userRole: values()) {
            if (userRole.label.equals(label)) {
                return userRole;
            }
        }
        return null;
    }
}

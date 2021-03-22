package modules.tables.enums;

public enum TableType {
    EMPLOYEES("Сотрудники"),
    DEPARTMENTS("Отделы"),
    EQUIPMENT("Оборудование"),
    PROFESSIONS("Профессии"),
    PROJECTS("Проекты"),
    CONTRACTS("Контракты"),
    SUBCONTRACTS("Субконтракты");

    private final String viewLabel;

    private TableType(String viewLabel) {
        this.viewLabel = viewLabel;
    }

    public static TableType valueOfLabel(String label) {
        for (TableType tableType : values()) {
            if (tableType.viewLabel.equals(label)) {
                return tableType;
            }
        }
        return null;
    }
}

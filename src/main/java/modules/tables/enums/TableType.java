package modules.tables.enums;

public enum TableType {
    EMPLOYEES("Сотрудники"),
    DEPARTMENTS("Отделы"),
    EQUIPMENT("Оборудование"),
    PROFESSIONS("Профессии"),
    PROJECTS("Проекты"),
    CONTRACTS("Договоры"),
    SUBCONTRACTS("Субдоговоры"),
    EQUIPMENTTYPE("Типы оборудования");

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

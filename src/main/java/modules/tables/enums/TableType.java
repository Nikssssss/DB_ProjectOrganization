package modules.tables.enums;

public enum TableType {
    EMPLOYEES("Сотрудники"),
    DEPARTMENTS("Отделы"),
    EQUIPMENT("Оборудование"),
    PROFESSIONS("Профессии"),
    PROJECTS("Проекты"),
    CONTRACTS("Договоры"),
    SUBCONTRACTS("Субдоговоры"),
    EQUIPMENTTYPE("Типы оборудования"),
    PROJECTS_EMPLOYEES("Исполнители проектов"),
    EQUIPMENT_PROJECTS("Оборудование на проектах"),
    TECHNICS("Техники"),
    ENGINEERS("Инженеры"),
    CONSTRUCTORS("Конструкторы"),
    ACCOUNTANTS("Бухгалтеры"),
    MANAGERS("Менеджеры");

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

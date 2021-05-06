package modules.queries.enums;

public enum QueryType {
    ORGANIZATION_ROSTER("Получить состав организации (с фильтрами)"),
    DEPARTMENT_MANAGERS("Получить перечень руководителей отделов"),
    CONTRACTS_LIST("Получить перечень договоров, выполняемых за указанный период"),
    PROJECTS_LIST("Получить перечень проектов, выполняемых за указанный период"),
    EQUIPMENT_PROJECTS("Получить распределение оборудования по проектам за указанный период"),
    PROJECTS_OF_CONTRACT( "Получить все проекты указанного договора"),
    CONTRACT_OF_PROJECT("Получить договор, поддерживаемый указанным проектом"),
    EQUIPMENT_OF_PROJECT("Получить оборудование, использующееся указанным проектом"),
    EMPLOYEES_OF_PROJECT("Получить сотрудников, участвующих в указанном проекте"),
    COST_OF_PROJECTS("Получить стоимость выполненных договоров за указанный период"),
    SUBPROJECTS_LIST("Получить перечень проектов, выполненных субподрядными организациями"),
    SUBPROJECTS_COST("Получить стоимость проектов, выполненных субподрядными организациями"),
    PROJECT_ROSTER_COUNT("Получить число сотрудников, работающих в указанном проекте"),
    EQUIPMENT_EFFICIENCY("Получить данные об эффективности оборудования"),
    CONTRACT_EFFICIENCY("Получить данные об эффективности договоров"),
    PROJECT_EFFICIENCY("Получить данные об эффективности проектов");

    private final String queryDescription;

    private QueryType(String queryDescription) {
        this.queryDescription = queryDescription;
    }

    public String getQueryDescription() {
        return queryDescription;
    }

    public static QueryType valueOfQueryDescription(String queryDescription) {
        for (QueryType queryType: values()) {
            if (queryType.queryDescription.equals(queryDescription)) {
                return queryType;
            }
        }
        return null;
    }
}

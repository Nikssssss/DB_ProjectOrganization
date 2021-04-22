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
    EMPLOYEES_OF_PROJECT("Получить сотрудников, участвующих в указанном проекте");

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

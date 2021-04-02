package modules.queries.enums;

public enum QueryType {
    ORGANIZATION_ROSTER(0, "Получить состав организации (с фильтрами)");
//    DEPARTMENT_ROSTER(1, "Получить состав указанного отдела (с фильтрами)");

    private final int rawValue;
    private final String queryDescription;

    private QueryType(int rawValue, String queryDescription) {
        this.rawValue = rawValue;
        this.queryDescription = queryDescription;
    }

    public int getRawValue() {
        return rawValue;
    }

    public String getQueryDescription() {
        return queryDescription;
    }

    public static QueryType valueOfRaw(int rawValue) {
        for (QueryType queryType: values()) {
            if (queryType.rawValue == rawValue) {
                return queryType;
            }
        }
        return null;
    }
}

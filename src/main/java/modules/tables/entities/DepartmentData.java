package modules.tables.entities;

public class DepartmentData {
    private final Integer departmentId;
    private final String departmentName;
    private final int managerId;

    public DepartmentData(Integer departmentId, String departmentName, int managerId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getManagerId() {
        return managerId;
    }
}

package common.entities;

public class DepartmentData {
    private final Integer departmentId;
    private final String departmentName;
    private final Integer managerId;

    public DepartmentData(Integer departmentId, String departmentName, Integer managerId) {
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

    public Integer getManagerId() {
        return managerId;
    }
}

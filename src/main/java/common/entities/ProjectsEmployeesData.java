package common.entities;

public class ProjectsEmployeesData {
    private final Integer employeeId;
    private final Integer projectId;

    public ProjectsEmployeesData(Integer employeeId, Integer projectId) {
        this.employeeId = employeeId;
        this.projectId = projectId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Integer getProjectId() {
        return projectId;
    }
}

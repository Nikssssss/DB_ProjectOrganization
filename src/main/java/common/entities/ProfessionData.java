package common.entities;

public class ProfessionData {
    private final Integer professionId;
    private final String professionName;
    private final Integer managementAbility;
    private final Integer departmentId;

    public ProfessionData(Integer professionId, String professionName, Integer managementAbility, Integer departmentId) {
        this.professionId = professionId;
        this.professionName = professionName;
        this.managementAbility = managementAbility;
        this.departmentId = departmentId;
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public String getProfessionName() {
        return professionName;
    }

    public Integer getManagementAbility() {
        return managementAbility;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }
}

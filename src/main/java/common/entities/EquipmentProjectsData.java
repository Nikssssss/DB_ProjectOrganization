package common.entities;

public class EquipmentProjectsData {
    private final Integer equipmentId;
    private final Integer projectId;

    public EquipmentProjectsData(Integer equipmentId, Integer projectId) {
        this.equipmentId = equipmentId;
        this.projectId = projectId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public Integer getProjectId() {
        return projectId;
    }
}

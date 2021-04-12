package common.entities;

public class EquipmentData {
    private final Integer equipmentId;
    private final String equipmentName;
    private final String equipmentType;
    private final Integer departmentId;

    public EquipmentData(Integer equipmentId, String equipmentName, String equipmentType, Integer departmentId) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentType = equipmentType;
        this.departmentId = departmentId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }
}

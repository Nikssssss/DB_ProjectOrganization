package common.entities;

public class EquipmentData {
    private final Integer equipmentId;
    private final String equipmentName;
    private final Integer equipmentTypeId;
    private final Integer departmentId;

    public EquipmentData(Integer equipmentId, String equipmentName, Integer equipmentTypeId, Integer departmentId) {
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentTypeId = equipmentTypeId;
        this.departmentId = departmentId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public Integer getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }
}

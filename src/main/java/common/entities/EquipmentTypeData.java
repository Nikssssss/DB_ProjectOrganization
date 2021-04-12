package common.entities;

public class EquipmentTypeData {
    private final Integer equipmentTypeId;
    private final String equipmentTypeName;

    public EquipmentTypeData(Integer equipmentTypeId, String equipmentTypeName) {
        this.equipmentTypeId = equipmentTypeId;
        this.equipmentTypeName = equipmentTypeName;
    }

    public Integer getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }
}

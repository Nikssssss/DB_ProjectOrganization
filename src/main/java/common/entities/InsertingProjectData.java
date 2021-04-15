package common.entities;

import java.sql.Date;
import java.util.ArrayList;

public class InsertingProjectData {
    private final Integer projectId;
    private final Integer contractId;
    private final Integer managerId;
    private final ArrayList<Integer> employees;
    private final ArrayList<Integer> equipment;
    private final Integer projectCost;
    private final Date startDate;
    private final Date finishDate;

    public InsertingProjectData(Integer projectId, Integer contractId, Integer managerId, ArrayList<Integer> employees,
                                ArrayList<Integer> equipment, Integer projectCost, Date startDate, Date finishDate) {
        this.projectId = projectId;
        this.contractId = contractId;
        this.managerId = managerId;
        this.employees = employees;
        this.equipment = equipment;
        this.projectCost = projectCost;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public ArrayList<Integer> getEmployees() {
        return employees;
    }

    public ArrayList<Integer> getEquipment() {
        return equipment;
    }

    public Integer getProjectCost() {
        return projectCost;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}

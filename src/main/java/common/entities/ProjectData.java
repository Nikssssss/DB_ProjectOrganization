package common.entities;

import java.sql.Date;

public class ProjectData {
    private final Integer projectId;
    private final String projectName;
    private final Integer projectManager;
    private final Integer projectCost;
    private final Date startDate;
    private final Date finishDate;

    public ProjectData(Integer projectId, String projectName, Integer projectManager, Integer projectCost, Date startDate, Date finishDate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectManager = projectManager;
        this.projectCost = projectCost;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public Integer getProjectManager() {
        return projectManager;
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

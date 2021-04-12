package common.entities;

import java.sql.Date;

public class SubcontractData {
    private final Integer subcontractId;
    private final String subcontractorName;
    private final Date startDate;
    private final Date finishDate;

    public SubcontractData(Integer subcontractId, String subcontractorName, Date startDate, Date finishDate) {
        this.subcontractId = subcontractId;
        this.subcontractorName = subcontractorName;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Integer getSubcontractId() {
        return subcontractId;
    }

    public String getSubcontractorName() {
        return subcontractorName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}

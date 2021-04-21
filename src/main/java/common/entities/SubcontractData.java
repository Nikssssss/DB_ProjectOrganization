package common.entities;

import java.sql.Date;

public class SubcontractData {
    private final Integer subcontractId;
    private final String subcontractName;
    private final String subcontractorName;
    private final Date startDate;
    private final Date finishDate;

    public SubcontractData(Integer subcontractId, String subcontractName, String subcontractorName, Date startDate, Date finishDate) {
        this.subcontractId = subcontractId;
        this.subcontractName = subcontractName;
        this.subcontractorName = subcontractorName;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Integer getSubcontractId() {
        return subcontractId;
    }

    public String getSubcontractName() {
        return subcontractName;
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

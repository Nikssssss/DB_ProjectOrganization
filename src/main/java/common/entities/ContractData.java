package common.entities;

import java.sql.Date;

public class ContractData {
    private final Integer contractId;
    private final Integer contractManager;
    private final Date startDate;
    private final Date finishDate;

    public ContractData(Integer contractId, Integer contractManager, Date startDate, Date finishDate) {
        this.contractId = contractId;
        this.contractManager = contractManager;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public Integer getContractId() {
        return contractId;
    }

    public Integer getContractManager() {
        return contractManager;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}

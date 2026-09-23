package com.labourlink.dto;

public class EndorsementRequest {
    private Long workHistoryId;
    private Long contractorId;
    private String skillTag;

    public Long getWorkHistoryId() { return workHistoryId; }
    public void setWorkHistoryId(Long workHistoryId) { this.workHistoryId = workHistoryId; }

    public Long getContractorId() { return contractorId; }
    public void setContractorId(Long contractorId) { this.contractorId = contractorId; }

    public String getSkillTag() { return skillTag; }
    public void setSkillTag(String skillTag) { this.skillTag = skillTag; }
}

package com.labourlink.dto;

public class RatingRequest {
    private Long workHistoryId;
    private Long raterId; // who is rating
    private Integer rating; // 1-5
    private String note;

    public Long getWorkHistoryId() { return workHistoryId; }
    public void setWorkHistoryId(Long workHistoryId) { this.workHistoryId = workHistoryId; }

    public Long getRaterId() { return raterId; }
    public void setRaterId(Long raterId) { this.raterId = raterId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}

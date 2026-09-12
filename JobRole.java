package com.skillnavi.model;

public class JobRole {
    private Integer jobId;
    private String jobTitle;
    private String description;

    public JobRole() {
    }

    public JobRole(String jobTitle, String description) {
        this.jobTitle = jobTitle;
        this.description = description;
    }

    public JobRole(Integer jobId, String jobTitle, String description) {
        this(jobTitle, description);
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return jobTitle;
    }
}

package com.skillnavi.model;

import com.skillnavi.model.enums.ProgressStatus;

public class Progress {
    private int studentId;
    private Skill skill;
    private int progressPercentage;
    private ProgressStatus status;

    public Progress(int studentId, Skill skill, int progressPercentage, ProgressStatus status) {
        this.studentId = studentId;
        this.skill = skill;
        this.progressPercentage = progressPercentage;
        this.status = status;
    }

    public int getStudentId() {
        return studentId;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(int progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public ProgressStatus getStatus() {
        return status;
    }

    public void setStatus(ProgressStatus status) {
        this.status = status;
    }
}

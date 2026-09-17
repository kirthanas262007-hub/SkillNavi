package com.skillnavi.model;

import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.model.enums.SkillPriority;

public class JobSkill {
    private int jobId;
    private Skill skill;
    private ProficiencyLevel requiredLevel;
    private SkillPriority priority;

    public JobSkill(int jobId, Skill skill, ProficiencyLevel requiredLevel, SkillPriority priority) {
        this.jobId = jobId;
        this.skill = skill;
        this.requiredLevel = requiredLevel;
        this.priority = priority;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public ProficiencyLevel getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(ProficiencyLevel requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public SkillPriority getPriority() {
        return priority;
    }

    public void setPriority(SkillPriority priority) {
        this.priority = priority;
    }
}

package com.skillnavi.model;

public class LearningResource {
    private Integer resourceId;
    private Skill skill;
    private String resourceName;
    private String resourceType;
    private String resourceLink;

    public LearningResource(Integer resourceId, Skill skill, String resourceName,
                            String resourceType, String resourceLink) {
        this.resourceId = resourceId;
        this.skill = skill;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceLink = resourceLink;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public Skill getSkill() {
        return skill;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceLink() {
        return resourceLink;
    }
}

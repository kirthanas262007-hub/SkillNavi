package com.skillnavi.model;

public class Skill {
    private Integer skillId;
    private String skillName;
    private String category;

    public Skill() {
    }

    public Skill(String skillName, String category) {
        this.skillName = skillName;
        this.category = category;
    }

    public Skill(Integer skillId, String skillName, String category) {
        this(skillName, category);
        this.skillId = skillId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return skillName;
    }
}

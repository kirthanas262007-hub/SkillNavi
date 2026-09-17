package com.skillnavi.model;

import com.skillnavi.model.enums.ProficiencyLevel;

public class StudentSkill {
    private int studentId;
    private Skill skill;
    private ProficiencyLevel proficiencyLevel;

    public StudentSkill(int studentId, Skill skill, ProficiencyLevel proficiencyLevel) {
        this.studentId = studentId;
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}

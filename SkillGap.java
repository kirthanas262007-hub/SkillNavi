package com.skillnavi.model;

import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.model.enums.SkillPriority;

public class SkillGap {
    private final Skill skill;
    private final ProficiencyLevel currentLevel;
    private final ProficiencyLevel requiredLevel;
    private final SkillPriority priority;
    private final GapStatus status;

    public SkillGap(Skill skill, ProficiencyLevel currentLevel, ProficiencyLevel requiredLevel,
                    SkillPriority priority, GapStatus status) {
        this.skill = skill;
        this.currentLevel = currentLevel;
        this.requiredLevel = requiredLevel;
        this.priority = priority;
        this.status = status;
    }

    public Skill getSkill() {
        return skill;
    }

    public ProficiencyLevel getCurrentLevel() {
        return currentLevel;
    }

    public ProficiencyLevel getRequiredLevel() {
        return requiredLevel;
    }

    public SkillPriority getPriority() {
        return priority;
    }

    public GapStatus getStatus() {
        return status;
    }

    public boolean isAdequatelyMatched() {
        return status == GapStatus.MATCHED;
    }
}

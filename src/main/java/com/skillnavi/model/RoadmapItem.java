package com.skillnavi.model;

import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.model.enums.SkillPriority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoadmapItem {
    private final int sequenceNumber;
    private final Skill skill;
    private final String action;
    private final SkillPriority priority;
    private final GapStatus gapStatus;
    private final List<LearningResource> resources;

    public RoadmapItem(int sequenceNumber, Skill skill, String action, SkillPriority priority,
                       GapStatus gapStatus, List<LearningResource> resources) {
        this.sequenceNumber = sequenceNumber;
        this.skill = skill;
        this.action = action;
        this.priority = priority;
        this.gapStatus = gapStatus;
        this.resources = new ArrayList<>(resources);
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public Skill getSkill() {
        return skill;
    }

    public String getAction() {
        return action;
    }

    public SkillPriority getPriority() {
        return priority;
    }

    public GapStatus getGapStatus() {
        return gapStatus;
    }

    public List<LearningResource> getResources() {
        return Collections.unmodifiableList(resources);
    }
}

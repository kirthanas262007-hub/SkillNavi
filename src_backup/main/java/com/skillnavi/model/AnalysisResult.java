package com.skillnavi.model;

import com.skillnavi.model.enums.GapStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnalysisResult {
    private final Student student;
    private final JobRole targetJobRole;
    private final List<SkillGap> skillGaps;
    private final List<RoadmapItem> roadmapItems;
    private final int readinessScore;

    public AnalysisResult(Student student, JobRole targetJobRole, List<SkillGap> skillGaps,
                          List<RoadmapItem> roadmapItems, int readinessScore) {
        this.student = student;
        this.targetJobRole = targetJobRole;
        this.skillGaps = new ArrayList<>(skillGaps);
        this.roadmapItems = new ArrayList<>(roadmapItems);
        this.readinessScore = readinessScore;
    }

    public Student getStudent() {
        return student;
    }

    public JobRole getTargetJobRole() {
        return targetJobRole;
    }

    public List<SkillGap> getSkillGaps() {
        return Collections.unmodifiableList(skillGaps);
    }

    public List<RoadmapItem> getRoadmapItems() {
        return Collections.unmodifiableList(roadmapItems);
    }

    public int getReadinessScore() {
        return readinessScore;
    }

    public long getCountByStatus(GapStatus status) {
        return skillGaps.stream().filter(gap -> gap.getStatus() == status).count();
    }
}

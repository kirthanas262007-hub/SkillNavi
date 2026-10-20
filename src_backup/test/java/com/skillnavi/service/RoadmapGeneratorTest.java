package com.skillnavi.service;

import com.skillnavi.model.RoadmapItem;
import com.skillnavi.model.Skill;
import com.skillnavi.model.SkillGap;
import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.model.enums.SkillPriority;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoadmapGeneratorTest {
    private final RoadmapGenerator generator = new RoadmapGenerator();

    @Test
    void ordersHighPriorityGapsBeforeLowerPriorityGaps() {
        Skill git = new Skill(1, "Git", "Tools");
        Skill springBoot = new Skill(2, "Spring Boot", "Backend Development");
        Skill restApi = new Skill(3, "REST API", "Backend Development");

        List<SkillGap> gaps = List.of(
                new SkillGap(git, null, ProficiencyLevel.INTERMEDIATE,
                        SkillPriority.MEDIUM, GapStatus.MISSING),
                new SkillGap(springBoot, ProficiencyLevel.BEGINNER, ProficiencyLevel.INTERMEDIATE,
                        SkillPriority.HIGH, GapStatus.NEEDS_IMPROVEMENT),
                new SkillGap(restApi, null, ProficiencyLevel.INTERMEDIATE,
                        SkillPriority.HIGH, GapStatus.MISSING)
        );

        List<RoadmapItem> roadmap = generator.generate(gaps, Map.of());

        assertEquals("REST API", roadmap.get(0).getSkill().getSkillName());
        assertEquals("Spring Boot", roadmap.get(1).getSkill().getSkillName());
        assertEquals("Git", roadmap.get(2).getSkill().getSkillName());
    }
}

package com.skillnavi.service;

import com.skillnavi.model.Skill;
import com.skillnavi.model.SkillGap;
import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.model.enums.SkillPriority;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReadinessCalculatorTest {
    private final ReadinessCalculator calculator = new ReadinessCalculator();

    @Test
    void calculatesCoverageUsingOnlyMatchedRequiredSkills() {
        Skill java = new Skill(1, "Java", "Programming");
        Skill sql = new Skill(2, "SQL", "Database");
        Skill git = new Skill(3, "Git", "Tools");
        Skill springBoot = new Skill(4, "Spring Boot", "Backend Development");
        Skill restApi = new Skill(5, "REST API", "Backend Development");

        List<SkillGap> gaps = List.of(
                new SkillGap(java, ProficiencyLevel.ADVANCED, ProficiencyLevel.ADVANCED,
                        SkillPriority.HIGH, GapStatus.MATCHED),
                new SkillGap(sql, ProficiencyLevel.INTERMEDIATE, ProficiencyLevel.INTERMEDIATE,
                        SkillPriority.HIGH, GapStatus.MATCHED),
                new SkillGap(git, ProficiencyLevel.INTERMEDIATE, ProficiencyLevel.INTERMEDIATE,
                        SkillPriority.MEDIUM, GapStatus.MATCHED),
                new SkillGap(springBoot, ProficiencyLevel.BEGINNER, ProficiencyLevel.INTERMEDIATE,
                        SkillPriority.HIGH, GapStatus.NEEDS_IMPROVEMENT),
                new SkillGap(restApi, null, ProficiencyLevel.INTERMEDIATE,
                        SkillPriority.HIGH, GapStatus.MISSING)
        );

        assertEquals(60, calculator.calculate(gaps));
    }
}

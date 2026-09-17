package com.skillnavi.service;

import com.skillnavi.model.JobSkill;
import com.skillnavi.model.Skill;
import com.skillnavi.model.SkillGap;
import com.skillnavi.model.StudentSkill;
import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.model.enums.ProficiencyLevel;
import com.skillnavi.model.enums.SkillPriority;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkillGapAnalyzerTest {
    private final SkillGapAnalyzer analyzer = new SkillGapAnalyzer();

    @Test
    void classifiesMatchedImprovementAndMissingSkills() {
        Skill java = new Skill(1, "Java", "Programming");
        Skill sql = new Skill(2, "SQL", "Database");
        Skill git = new Skill(3, "Git", "Tools");

        List<JobSkill> requiredSkills = List.of(
                new JobSkill(1, java, ProficiencyLevel.ADVANCED, SkillPriority.HIGH),
                new JobSkill(1, sql, ProficiencyLevel.INTERMEDIATE, SkillPriority.HIGH),
                new JobSkill(1, git, ProficiencyLevel.BEGINNER, SkillPriority.MEDIUM)
        );
        List<StudentSkill> studentSkills = List.of(
                new StudentSkill(1, java, ProficiencyLevel.ADVANCED),
                new StudentSkill(1, sql, ProficiencyLevel.BEGINNER)
        );

        List<SkillGap> gaps = analyzer.analyze(requiredSkills, studentSkills);

        assertEquals(GapStatus.MATCHED, gaps.get(0).getStatus());
        assertEquals(GapStatus.NEEDS_IMPROVEMENT, gaps.get(1).getStatus());
        assertEquals(GapStatus.MISSING, gaps.get(2).getStatus());
    }
}

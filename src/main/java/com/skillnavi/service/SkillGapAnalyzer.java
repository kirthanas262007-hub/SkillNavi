package com.skillnavi.service;

import com.skillnavi.model.JobSkill;
import com.skillnavi.model.SkillGap;
import com.skillnavi.model.StudentSkill;
import com.skillnavi.model.enums.GapStatus;
import com.skillnavi.model.enums.ProficiencyLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillGapAnalyzer {
    public List<SkillGap> analyze(List<JobSkill> requiredSkills, List<StudentSkill> studentSkills) {
        Map<Integer, StudentSkill> studentSkillsById = indexStudentSkills(studentSkills);
        List<SkillGap> gaps = new ArrayList<>();

        for (JobSkill jobSkill : requiredSkills) {
            StudentSkill studentSkill = studentSkillsById.get(jobSkill.getSkill().getSkillId());
            ProficiencyLevel currentLevel = studentSkill == null ? null : studentSkill.getProficiencyLevel();
            GapStatus status = determineStatus(currentLevel, jobSkill.getRequiredLevel());

            gaps.add(new SkillGap(
                    jobSkill.getSkill(),
                    currentLevel,
                    jobSkill.getRequiredLevel(),
                    jobSkill.getPriority(),
                    status
            ));
        }
        return gaps;
    }

    private Map<Integer, StudentSkill> indexStudentSkills(List<StudentSkill> studentSkills) {
        Map<Integer, StudentSkill> skillsById = new HashMap<>();
        for (StudentSkill studentSkill : studentSkills) {
            skillsById.put(studentSkill.getSkill().getSkillId(), studentSkill);
        }
        return skillsById;
    }

    private GapStatus determineStatus(ProficiencyLevel currentLevel, ProficiencyLevel requiredLevel) {
        if (currentLevel == null) {
            return GapStatus.MISSING;
        }
        if (currentLevel.meetsOrExceeds(requiredLevel)) {
            return GapStatus.MATCHED;
        }
        return GapStatus.NEEDS_IMPROVEMENT;
    }
}

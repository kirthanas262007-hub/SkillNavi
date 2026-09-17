package com.skillnavi.service;

import com.skillnavi.model.SkillGap;
import com.skillnavi.model.enums.GapStatus;

import java.util.List;

public class ReadinessCalculator {
    public int calculate(List<SkillGap> skillGaps) {
        if (skillGaps.isEmpty()) {
            return 0;
        }

        long matchedSkills = skillGaps.stream()
                .filter(gap -> gap.getStatus() == GapStatus.MATCHED)
                .count();

        return (int) Math.round((matchedSkills * 100.0) / skillGaps.size());
    }
}

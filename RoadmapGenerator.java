package com.skillnavi.service;

import com.skillnavi.model.LearningResource;
import com.skillnavi.model.RoadmapItem;
import com.skillnavi.model.SkillGap;
import com.skillnavi.model.enums.GapStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class RoadmapGenerator {
    public List<RoadmapItem> generate(List<SkillGap> skillGaps,
                                      Map<Integer, List<LearningResource>> resourcesBySkillId) {
        List<SkillGap> gapsToAddress = skillGaps.stream()
                .filter(gap -> gap.getStatus() != GapStatus.MATCHED)
                .sorted(Comparator
                        .comparingInt((SkillGap gap) -> gap.getPriority().getWeight()).reversed()
                        .thenComparing(Comparator.comparingInt(this::gapSeverity).reversed())
                        .thenComparing(gap -> gap.getSkill().getSkillName()))
                .toList();

        List<RoadmapItem> roadmap = new ArrayList<>();
        for (int index = 0; index < gapsToAddress.size(); index++) {
            SkillGap gap = gapsToAddress.get(index);
            List<LearningResource> resources = resourcesBySkillId
                    .getOrDefault(gap.getSkill().getSkillId(), List.of());
            roadmap.add(new RoadmapItem(
                    index + 1,
                    gap.getSkill(),
                    createAction(gap),
                    gap.getPriority(),
                    gap.getStatus(),
                    resources
            ));
        }
        return roadmap;
    }

    private int gapSeverity(SkillGap gap) {
        return gap.getStatus() == GapStatus.MISSING ? 2 : 1;
    }

    private String createAction(SkillGap gap) {
        String skillName = gap.getSkill().getSkillName();
        if (gap.getStatus() == GapStatus.MISSING) {
            return "Learn " + skillName + " up to " + gap.getRequiredLevel() + " level.";
        }
        return "Improve " + skillName + " from " + gap.getCurrentLevel()
                + " to " + gap.getRequiredLevel() + " level.";
    }
}

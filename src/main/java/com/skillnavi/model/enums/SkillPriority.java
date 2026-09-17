package com.skillnavi.model.enums;

import java.util.Locale;

public enum SkillPriority {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    private final int weight;

    SkillPriority(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public static SkillPriority fromDatabaseValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Skill priority is required.");
        }

        try {
            return SkillPriority.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown skill priority: " + value, exception);
        }
    }
}

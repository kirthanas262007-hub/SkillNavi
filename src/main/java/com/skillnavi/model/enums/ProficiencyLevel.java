package com.skillnavi.model.enums;

import java.util.Locale;

public enum ProficiencyLevel {
    BEGINNER(1),
    INTERMEDIATE(2),
    ADVANCED(3);

    private final int numericValue;

    ProficiencyLevel(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public boolean meetsOrExceeds(ProficiencyLevel requiredLevel) {
        return numericValue >= requiredLevel.numericValue;
    }

    public static ProficiencyLevel fromDatabaseValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Proficiency level is required.");
        }

        try {
            return ProficiencyLevel.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown proficiency level: " + value, exception);
        }
    }
}

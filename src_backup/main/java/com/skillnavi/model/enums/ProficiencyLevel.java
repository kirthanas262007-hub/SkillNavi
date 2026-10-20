
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
            throw new IllegalArgumentException(
                    "Proficiency level is required."
            );
        }

        String normalized = value.trim().toUpperCase(Locale.ROOT);

        // Support numeric values stored in the database.
        try {
            return fromNumericValue(Integer.parseInt(normalized));
        } catch (NumberFormatException ignored) {
            // The value may instead be an enum name.
        }

        try {
            return ProficiencyLevel.valueOf(normalized);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Unknown proficiency level: " + value,
                    exception
            );
        }
    }

    public static ProficiencyLevel fromNumericValue(int value) {
        for (ProficiencyLevel level : values()) {
            if (level.numericValue == value) {
                return level;
            }
        }

        throw new IllegalArgumentException(
                "Unknown proficiency level: " + value
        );
    }
}

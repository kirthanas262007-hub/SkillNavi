package com.skillnavi.model.enums;

import java.util.Locale;

public enum ProgressStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED;

    public static ProgressStatus fromDatabaseValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Progress status is required.");
        }

        try {
            return ProgressStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown progress status: " + value, exception);
        }
    }
}

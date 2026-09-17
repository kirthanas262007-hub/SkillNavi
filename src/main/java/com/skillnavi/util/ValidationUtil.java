package com.skillnavi.util;

import java.util.regex.Pattern;

public final class ValidationUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private ValidationUtil() {
    }

    public static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        return value.trim();
    }

    public static String requireValidEmail(String email) {
        String cleanedEmail = requireText(email, "Email");
        if (!EMAIL_PATTERN.matcher(cleanedEmail).matches()) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        return cleanedEmail;
    }

    public static int requireProgressPercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Progress percentage must be between 0 and 100.");
        }
        return percentage;
    }
}

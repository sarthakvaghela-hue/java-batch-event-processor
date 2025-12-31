package com.deccan.batchprocessor.validator;

import com.deccan.batchprocessor.model.EventType;

public class EventValidator {

    public boolean isValid(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }

        String[] parts = line.split(",", -1);
        if (parts.length != 4) {
            return false;
        }

        if (parts[0].trim().isEmpty()) {
            return false;
        }

        try {
            EventType.fromString(parts[1]);
            int value = Integer.parseInt(parts[2].trim());
            if (value < 0) {
                return false;
            }
            Long.parseLong(parts[3].trim());
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
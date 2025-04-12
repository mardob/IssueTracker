package com.issueTracker.model;

public enum Status {
    OPEN,
    IN_PROGRESS,
    CLOSED;

    public static Status findByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

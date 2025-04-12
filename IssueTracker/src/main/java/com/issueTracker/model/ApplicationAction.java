package com.issueTracker.model;

public enum ApplicationAction {
         CREATE,
         UPDATE,
         LIST;

    public static ApplicationAction findByName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

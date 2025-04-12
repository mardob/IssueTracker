package com.issueTracker.model;

public record Issue(String id, String description, String parentIssueId, Status status) {}

package com.issueTracker.service;

import com.issueTracker.model.Status;

public interface IssueTrackerService {
    void createIssue(String description, String parentIssueId);

    void updateIssue(String id, Status status);

    void getAllIssues(Status status);
}

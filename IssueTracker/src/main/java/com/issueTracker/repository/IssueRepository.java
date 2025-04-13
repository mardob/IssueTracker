package com.issueTracker.repository;

import com.issueTracker.model.Issue;
import com.issueTracker.model.Status;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IssueRepository {

    void createIssue(String description, String parentIssueId);

    void updateIssue(Integer id, Status status);

    List<Issue> getAllIssues();
}

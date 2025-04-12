package com.issueTracker.service;

import com.issueTracker.model.Issue;
import com.issueTracker.model.Status;
import com.issueTracker.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueTrackerServiceImpl implements IssueTrackerService{

    TicketRepository ticketRepository;

    public IssueTrackerServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void createIssue(String description, String parentIssueId) {
        ticketRepository.createIssue(description, parentIssueId);
    }

    @Override
    public void updateIssue(String id, Status status) {
        ticketRepository.updateIssue(id, status);
    }

    @Override
    public void getAllIssues(Status status) {
        List<Issue> issues = ticketRepository.getAllIssues();
        issues.stream().filter(
                issue -> status.equals(issue.status())).forEach(System.out::println);
    }
}

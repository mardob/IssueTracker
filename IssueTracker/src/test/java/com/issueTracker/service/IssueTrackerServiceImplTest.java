package com.issueTracker.service;

import com.issueTracker.model.Status;
import com.issueTracker.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class IssueTrackerServiceImplTest {
    @Mock
    TicketRepository ticketRepository;

    @InjectMocks
    IssueTrackerServiceImpl issueTrackerServiceImplTest;

    @Test
    public void GIVEN_status_WHEN_getAllIssues_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        Status status = Status.OPEN;

        //WHEN
        issueTrackerServiceImplTest.getAllIssues(status);

        //THEN
        verify(ticketRepository).getAllIssues();
        verify(ticketRepository, never()).createIssue(any(), any());
        verify(ticketRepository, never()).updateIssue(any(), any());
    }

    @Test
    public void GIVEN_parentIssueIdAndDescription_WHEN_createIssue_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        String parentIssueId = "1";
        String description = "Mock description";

        //WHEN
        issueTrackerServiceImplTest.createIssue(description, parentIssueId);

        //THEN
        verify(ticketRepository, never()).getAllIssues();
        verify(ticketRepository).createIssue(description, parentIssueId);
        verify(ticketRepository, never()).updateIssue(any(), any());
    }


    @Test
    public void GIVEN_idAndStatus_WHEN_updateIssue_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        String id = "1";
        Status status = Status.OPEN;

        //WHEN
        issueTrackerServiceImplTest.updateIssue(id, status);

        //THEN
        verify(ticketRepository, never()).getAllIssues();
        verify(ticketRepository, never()).createIssue(any(), any());
        verify(ticketRepository).updateIssue(id, status);
    }
}

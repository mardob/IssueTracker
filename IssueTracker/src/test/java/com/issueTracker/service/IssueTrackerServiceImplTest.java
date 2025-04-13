package com.issueTracker.service;

import com.issueTracker.model.Status;
import com.issueTracker.repository.IssueRepository;
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
    IssueRepository issueRepository;

    @InjectMocks
    IssueTrackerServiceImpl issueTrackerServiceImplTest;

    @Test
    public void GIVEN_status_WHEN_getAllIssues_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        Status status = Status.OPEN;

        //WHEN
        issueTrackerServiceImplTest.getAllIssues(status);

        //THEN
        verify(issueRepository).getAllIssues();
        verify(issueRepository, never()).createIssue(any(), any());
        verify(issueRepository, never()).updateIssue(any(), any());
    }

    @Test
    public void GIVEN_noStatus_WHEN_getAllIssues_THEN_noRepositoryMethodCalled() {
        //GIVEN

        //WHEN
        issueTrackerServiceImplTest.getAllIssues(null);

        //THEN
        verify(issueRepository, never()).getAllIssues();
        verify(issueRepository, never()).createIssue(any(), any());
        verify(issueRepository, never()).updateIssue(any(), any());
    }

    @Test
    public void GIVEN_parentIssueIdAndDescription_WHEN_createIssue_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        String parentIssueId = "1";
        String description = "Mock description";

        //WHEN
        issueTrackerServiceImplTest.createIssue(description, parentIssueId);

        //THEN
        verify(issueRepository, never()).getAllIssues();
        verify(issueRepository).createIssue(description, parentIssueId);
        verify(issueRepository, never()).updateIssue(any(), any());
    }

    @Test
    public void GIVEN_notANUmberAsIdAndDescription_WHEN_createIssue_THEN_noRepositoryMethodCalled() {
        //GIVEN
        String parentIssueId = "notNumber";
        String description = "Mock description";

        //WHEN
        issueTrackerServiceImplTest.createIssue(description, parentIssueId);

        //THEN
        verify(issueRepository, never()).getAllIssues();
        verify(issueRepository, never()).createIssue(any(), any());
        verify(issueRepository, never()).updateIssue(any(), any());
    }


    @Test
    public void GIVEN_idAndStatus_WHEN_updateIssue_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        String id = "1";
        Status status = Status.OPEN;

        //WHEN
        issueTrackerServiceImplTest.updateIssue(id, status);

        //THEN
        verify(issueRepository, never()).getAllIssues();
        verify(issueRepository, never()).createIssue(any(), any());
        verify(issueRepository).updateIssue(Integer.parseInt(id), status);
    }

    @Test
    public void GIVEN_notANUmberAsIdAndStatus_WHEN_updateIssue_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        String id = "notNumber";
        Status status = Status.OPEN;

        //WHEN
        issueTrackerServiceImplTest.updateIssue(id, status);

        //THEN
        verify(issueRepository, never()).getAllIssues();
        verify(issueRepository, never()).createIssue(any(), any());
        verify(issueRepository, never()).updateIssue(any(), any());
    }

    @Test
    public void GIVEN_idAndNoStatus_WHEN_updateIssue_THEN_correctRepositoryMethodCalled() {
        //GIVEN
        String id = "1";

        //WHEN
        issueTrackerServiceImplTest.updateIssue(id, null);

        //THEN
        verify(issueRepository, never()).getAllIssues();
        verify(issueRepository, never()).createIssue(any(), any());
        verify(issueRepository, never()).updateIssue(any(), any());
    }
}

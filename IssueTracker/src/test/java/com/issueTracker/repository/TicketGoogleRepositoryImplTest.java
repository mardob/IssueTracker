package com.issueTracker.repository;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.issueTracker.model.Status;
import com.issueTracker.util.SheetsServiceUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TicketGoogleRepositoryImplTest {

    @Mock
    SheetsServiceUtil sheetsServiceUtil;

    @InjectMocks
    IssueGoogleRepositoryImpl issueGoogleRepositoryImpl;

    @Test
    public void GIVEN_record_WHEN_getAllIssues_THEN_correctRepositoryMethodCalled() throws GeneralSecurityException, IOException {
        //GIVEN
        ValueRange valueRange = createValueRange();
        when(sheetsServiceUtil.getRecords(any())).thenReturn(valueRange);

        //WHEN
        List result = issueGoogleRepositoryImpl.getAllIssues();

        //THEN
        assert(result != null);
        assert(!result.isEmpty());
        verify(sheetsServiceUtil).getRecords(any());
        verify(sheetsServiceUtil, never()).createRecord(any(), any());
        verify(sheetsServiceUtil, never()).updateRecord(any(), any());
    }

    @Test
    public void GIVEN_descriptionAndNoParentIssueId_WHEN_createIssue_THEN_correctRepositoryMethodCalled() throws GeneralSecurityException, IOException {
        //GIVEN
        String description = "test description";

        //WHEN
        issueGoogleRepositoryImpl.createIssue(description, "");

        //THEN
        verify(sheetsServiceUtil).getRecords(any());
        verify(sheetsServiceUtil).createRecord(any(), any());
        verify(sheetsServiceUtil, never()).updateRecord(any(), any());
    }

    @Test
    public void GIVEN_descriptionAndParentIssueId_WHEN_createIssue_THEN_correctRepositoryMethodCalled() throws GeneralSecurityException, IOException {
        //GIVEN
        when(sheetsServiceUtil.getRecords(any())).thenReturn(createValueRange());
        String description = "test description";

        //WHEN
        issueGoogleRepositoryImpl.createIssue(description, "1");

        //THEN
        verify(sheetsServiceUtil).getRecords(any());
        verify(sheetsServiceUtil).createRecord(any(), any());
        verify(sheetsServiceUtil, never()).updateRecord(any(), any());
    }


    @Test
    public void GIVEN_idAndStatus_WHEN_updateIssue_THEN_correctRepositoryMethodCalled() throws GeneralSecurityException, IOException {
        //GIVEN
        ValueRange valueRange = createValueRange();
        when(sheetsServiceUtil.getRecords(any())).thenReturn(valueRange);
        Integer id = 1;
        Status status = Status.OPEN;

        //WHEN
        issueGoogleRepositoryImpl.updateIssue(id, status);

        //THEN
        verify(sheetsServiceUtil).getRecords(any());
        verify(sheetsServiceUtil, never()).createRecord(any(), any());
        verify(sheetsServiceUtil).updateRecord(any(), any());
    }

    @Test
    public void GIVEN_wrongIdAndStatus_WHEN_updateIssue_THEN_noUpdate() throws GeneralSecurityException, IOException {
        //GIVEN
        ValueRange valueRange = createValueRange();
        when(sheetsServiceUtil.getRecords(any())).thenReturn(valueRange);
        Integer id = 5;
        Status status = Status.OPEN;

        //WHEN
        issueGoogleRepositoryImpl.updateIssue(id, status);

        //THEN
        verify(sheetsServiceUtil).getRecords(any());
        verify(sheetsServiceUtil, never()).createRecord(any(), any());
        verify(sheetsServiceUtil, never()).updateRecord(any(), any());
    }


    private ValueRange createValueRange(){
        List<List<Object>> values = List.of(List.of("1", "description", "1", Status.OPEN.toString()));
        return  new ValueRange().setValues(values);
    }
}

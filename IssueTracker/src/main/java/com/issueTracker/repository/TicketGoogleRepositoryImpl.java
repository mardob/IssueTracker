package com.issueTracker.repository;

import com.issueTracker.model.Issue;
import com.issueTracker.model.Status;
import com.issueTracker.util.SheetsServiceUtil;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TicketGoogleRepositoryImpl implements TicketRepository{
    @Autowired
    private SheetsServiceUtil sheetsServiceUtil;

    @Override
    public void createIssue(String description, String parentIssueId) {

        /* While inefficient and not performant if table gets large it was done for the sake of saving dev time for this demo.
        More efficient approach could be for example storing it in separate field in sheet. */
        int currentId = getAllIssues().size();

        final String range = "A1";
        List<List<Object>> values = List.of(List.of(currentId++, description, parentIssueId, Status.OPEN.toString()));
        ValueRange valueRange = new ValueRange()
                .setValues(values);
        try {
            sheetsServiceUtil.createRecord(range, valueRange);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateIssue(String id, Status status) {
        final String range = "D" + id;
        List<List<Object>> values = List.of(List.of(status.toString()));
        ValueRange valueRange = new ValueRange()
                .setValues(values);
        try {
            sheetsServiceUtil.updateRecord(range, valueRange);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Issue> getAllIssues() {
        final String range = "A:D";
        ValueRange valueRange;
        try {
            valueRange = sheetsServiceUtil.getRecords(range);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        if(Objects.isNull(valueRange)){
            return new ArrayList<>();
        }

        return valueRange.getValues().stream().map(
                row -> new Issue(
                        (String) row.get(0),
                        (String) row.get(1),
                        (String) row.get(2),
                        Status.findByName((String) row.get(3))
                )
        ).toList();
    }


}

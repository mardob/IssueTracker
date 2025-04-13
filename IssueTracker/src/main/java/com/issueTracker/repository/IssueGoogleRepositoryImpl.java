package com.issueTracker.repository;

import com.issueTracker.model.Issue;
import com.issueTracker.model.Status;
import com.issueTracker.util.SheetsServiceUtil;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.issueTracker.util.ValidationUtil.ERROR_MESSAGE_ID_NOT_FOUND;

@Component
public class IssueGoogleRepositoryImpl implements IssueRepository {
    @Autowired
    private SheetsServiceUtil sheetsServiceUtil;

    @Override
    public void createIssue(String description, String parentIssueId) {

        /* While inefficient and not performant if table gets large it was done for the sake of saving dev time for this demo.
        More efficient approach could be for example storing it in separate field in sheet.
        This logic is kept here rather than service to make sure that this data provider can be easily swapped for others eg. Normal database. */
        int currentId = getAllIssues().size();
        if (Strings.isNotBlank(parentIssueId) && Integer.parseInt(parentIssueId) > currentId){
            System.out.printf(ERROR_MESSAGE_ID_NOT_FOUND, "Parent issue Id", parentIssueId);
            return;
        }

        final String range = "A1";
        List<List<Object>> values = List.of(List.of(currentId++, description, parentIssueId, Status.OPEN.toString()));
        ValueRange valueRange = new ValueRange()
                .setValues(values);
        try {
            sheetsServiceUtil.createRecord(range, valueRange);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Issue successfully created");
    }

    @Override
    public void updateIssue(Integer id, Status status) {
        int currentId = getAllIssues().size();
        if(id > currentId){
            System.out.printf(ERROR_MESSAGE_ID_NOT_FOUND, "Id", id);
            return;
        }

        final String range = "D" + (id+1);

        List<List<Object>> values = List.of(List.of(status.toString()));
        ValueRange valueRange = new ValueRange()
                .setValues(values);
        try {
            sheetsServiceUtil.updateRecord(range, valueRange);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Issue successfully updated");
    }

    @Override
    public List<Issue> getAllIssues() {
        final String range = "A:D";
        ValueRange valueRange;
        try {
            valueRange = sheetsServiceUtil.getRecords(range);
        } catch (IOException | GeneralSecurityException e) {
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

package com.issueTracker.service;

import ch.qos.logback.core.util.StringUtil;
import com.issueTracker.model.Issue;
import com.issueTracker.model.Status;
import com.issueTracker.repository.IssueRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.issueTracker.util.ValidationUtil.isValidInteger;
import static com.issueTracker.util.ValidationUtil.isValidStatus;

@Service
public class IssueTrackerServiceImpl implements IssueTrackerService{

    IssueRepository issueRepository;

    public IssueTrackerServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public void createIssue(String description, String parentIssueId) {
        if(StringUtil.notNullNorEmpty(description)
                && (Strings.isBlank(parentIssueId) || isValidInteger(parentIssueId))) {
            issueRepository.createIssue(description, parentIssueId);
        }
    }

    @Override
    public void updateIssue(String id, Status status) {
        if(isValidStatus(status) && isValidInteger(id)){
            issueRepository.updateIssue(Integer.parseInt(id), status);
        }
    }

    @Override
    public void getAllIssues(Status status) {
        if(isValidStatus(status)) {
            List<Issue> issues = issueRepository.getAllIssues();
            if(issues.size() == 1){
                //Just header returned
                System.out.println("No issues found");
                return;
            }
            System.out.println("Issues found:");
            issues.stream().filter(
                    issue -> status.equals(issue.status())).forEach(System.out::println);
        }
    }
}

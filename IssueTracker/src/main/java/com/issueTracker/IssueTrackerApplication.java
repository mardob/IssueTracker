package com.issueTracker;

import com.issueTracker.model.ApplicationAction;
import com.issueTracker.model.Status;
import com.issueTracker.service.IssueTrackerService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static com.issueTracker.util.ValidationUtil.*;

@SpringBootApplication
public class IssueTrackerApplication implements CommandLineRunner {

	@Autowired
	IssueTrackerService issueTrackerService;

	@Value("${google.spreadsheetId}")
	private String spreadsheetId;

	public static void main(String[] args) {
		SpringApplication.run(IssueTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) {
		final String ERROR_MESSAGE_UPDATE_INPUT_INVALID = String.format("Update requires [Existing Issue id] " +
				"of the Issue to be updated and [Status] to update this Issue to." +
				" List of valid Statuses is: %s", VALID_STATUS_LIST);

		if(Strings.isBlank(spreadsheetId)){
			System.out.println("Please read Readme and configure the application before use, SpreadsheetId not configured in application.properties");
			return;
		}

		if(isInvalidNumberOfInputs(args) || isNotValidActionToPerform(args)){
			return;
		}
		ApplicationAction applicationAction = ApplicationAction.findByName(args[0]);

		if(applicationAction == ApplicationAction.LIST){
			Status inputStatus = Status.findByName(args[1]);
			issueTrackerService.getAllIssues(inputStatus);
		} else if(applicationAction == ApplicationAction.CREATE){
			String parentIssueId = (args.length == 3 ? args[2] : "");
			issueTrackerService.createIssue(args[1], parentIssueId);
		} else if(applicationAction == ApplicationAction.UPDATE){
			if(args.length != 3){
				System.out.printf(ERROR_MESSAGE_NUMBER_OF_PARAMS, ERROR_MESSAGE_UPDATE_INPUT_INVALID);
				return;
			}
			issueTrackerService.updateIssue(args[1], Status.findByName(args[2]));
		} else{
			System.out.println(ERROR_MESSAGE_FIRST_PARAM);
		}
	}
}

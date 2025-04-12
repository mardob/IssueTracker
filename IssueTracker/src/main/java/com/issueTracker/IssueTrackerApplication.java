package com.issueTracker;

import com.issueTracker.model.ApplicationAction;
import com.issueTracker.model.Status;
import com.issueTracker.service.IssueTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IssueTrackerApplication implements CommandLineRunner {

	@Autowired
	IssueTrackerService issueTrackerService;

    public static void main(String[] args) {
		SpringApplication.run(IssueTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) {
        final String ERROR_MESSAGE_FIRST_PARAM = "First parameter must be one of the supported actions [CREATE, UPDATE, LIST]";

		//Check overall number of arguments
        if(args.length < 2 || args.length > 3 ){
			System.out.printf("Need correct number of inputs. %s", ERROR_MESSAGE_FIRST_PARAM);
			return;
		}

		//Check and populate the first argument
		ApplicationAction applicationAction = ApplicationAction.findByName(args[0]);
		if(applicationAction == null) {
			System.out.println(ERROR_MESSAGE_FIRST_PARAM);
			return;
		}

		if(applicationAction == ApplicationAction.LIST){
			issueTrackerService.getAllIssues(Status.findByName(args[1]));
		}

		if(applicationAction == ApplicationAction.CREATE){
			issueTrackerService.createIssue(args[1], args[2]);
		}

		if(applicationAction == ApplicationAction.UPDATE){
			issueTrackerService.updateIssue(args[1], Status.findByName(args[2]));
		}


	}
}

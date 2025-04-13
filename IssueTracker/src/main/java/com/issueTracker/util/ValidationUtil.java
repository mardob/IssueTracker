package com.issueTracker.util;

import com.issueTracker.model.ApplicationAction;
import com.issueTracker.model.Status;

import java.util.Objects;

public class ValidationUtil {

    final static public String VALID_STATUS_LIST = String.format("[%s, %s, %s]", Status.OPEN,Status.CLOSED,Status.IN_PROGRESS);
    final static String ERROR_MESSAGE_VALID_STATUS = String.format("Status must be one of the supported statuses %s\n"
            ,VALID_STATUS_LIST);
    final static String ERROR_MESSAGE_VALID_ID = "Identifier must be valid natural number bigger than 0. Eg. 1";
    final static public String ERROR_MESSAGE_ID_NOT_FOUND = "%s provided [%s] not found\n";
    final static public String ERROR_MESSAGE_FIRST_PARAM = "First parameter must be one of the supported actions [CREATE, UPDATE, LIST]";
    final static public String ERROR_MESSAGE_NUMBER_OF_PARAMS = "Incorrect number of inputs. %s";

    public static boolean isValidStatus(Status status){
        if(Objects.isNull(status)){
            System.out.println(ERROR_MESSAGE_VALID_STATUS);
            return false;
        }
        return true;
    }

    public static boolean isValidInteger(String input){
        try {
            int asInt = Integer.parseInt(input);
            if(asInt < 0){
                System.out.println(ERROR_MESSAGE_VALID_ID);
                return false;
            }
        } catch(NumberFormatException e){
            System.out.println(ERROR_MESSAGE_VALID_ID);
            return false;
        }
        return true;
    }

    public static boolean isInvalidNumberOfInputs(String... args){
        if(args.length < 2 || args.length > 3 ){
            System.out.printf("Incorrect number of inputs. %s", ERROR_MESSAGE_FIRST_PARAM);
            return true;
        }
        return false;
    }

    public static boolean isNotValidActionToPerform(String... args){
        ApplicationAction applicationAction = ApplicationAction.findByName(args[0]);
        if(applicationAction == null) {
            System.out.println(ERROR_MESSAGE_FIRST_PARAM);
            return true;
        }
        return false;
    }
}

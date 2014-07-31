package com.tasks.service;

import com.tasks.model.ToDoResponse;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;

import java.util.HashMap;
import java.util.Map;

/**
 * MessageService
 * Basic business tier component.
 * @version 1.0
 * @author Alejandro
 */
public class MessageService {
    /* Find your sid and token at twilio.com/user/account */
    public static final String ACCOUNT_SID = "AC055a4b586b8860df8e4211e049e13c04";
    public static final String AUTH_TOKEN = "383e5b490475d3d3e4eeac798dde1678";
    public static final String TWILIO_NUMBER="+1 650-285-5920";
    public static final String DEFAULT_NUMBER = "+1 650-276-8287";
    private static String toPhoneNumber;

    public static ToDoResponse sendMessage (String body)  {
        try {
             if (toPhoneNumber == null || toPhoneNumber.isEmpty()) {
             toPhoneNumber = DEFAULT_NUMBER;
             }
             TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
             Account account = client.getAccount();
             SmsFactory messageFactory = account.getSmsFactory();
             Map<String, String> params = new HashMap<String, String>();
             params.put("To", toPhoneNumber); // Replace with a valid phone number for your account.
             params.put("From", TWILIO_NUMBER); // Replace with a valid phone number for your account.
             params.put("Body", body);
             messageFactory.create(params);
             return new ToDoResponse(body + " --- And successfully sent SMS", true);
         }
         catch (TwilioRestException e){
             e.printStackTrace();
             return new ToDoResponse(body + " --- But failed sending SMS: " + e.getMessage(), true);
         }
    }

    public static void setToPhoneNumber(String newPhoneNumber) {
        toPhoneNumber = newPhoneNumber;
    }
}

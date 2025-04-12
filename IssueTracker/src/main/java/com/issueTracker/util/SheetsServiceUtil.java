package com.issueTracker.util;

import com.issueTracker.model.GoogleValueInputOption;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;


import static com.issueTracker.util.GoogleAuthorizeUtil.JSON_FACTORY;

@Service
public class SheetsServiceUtil {
    @Value("${google.applicationName}")
    private static String APPLICATION_NAME;

    @Value("${google.spreadsheetId}")
    private String spreadsheetId;

    @Autowired
    GoogleAuthorizeUtil googleAuthorizeUtil;

    public ValueRange getRecords(String dataRange) throws IOException, GeneralSecurityException {
        Sheets sheets = getSheets();
        return sheets.spreadsheets().values()
                .get(spreadsheetId, dataRange)
                .execute();
    }

    public void createRecord(String range, ValueRange body) throws IOException, GeneralSecurityException {
        Sheets sheets = getSheets();

        sheets.spreadsheets().values().append(spreadsheetId, range, body)
                .setValueInputOption(GoogleValueInputOption.RAW.toString())
                .execute();

    }

    public void updateRecord(String range, ValueRange body) throws IOException, GeneralSecurityException {
        Sheets sheets = getSheets();

        sheets.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption(GoogleValueInputOption.RAW.toString())
                .execute();
    }

    private Sheets getSheets() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleAuthorizeUtil.getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
    }

}

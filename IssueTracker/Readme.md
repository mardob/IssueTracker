About The Project:

This project is to create CLI Issue Tracking application that stores data in Google sheets.

This application supports following actions with specified inputs:

    A) Create a new issue with params:
        • description [required]
        • parent issue id [optional]
    B) Update the status of an existing issue with the following parameters:
        • the existing issue id [required]
        • status (OPEN, IN_PROGRESS, CLOSED) [required]
    C) List all existing issues by the following parameters:
        • status (OPEN, IN_PROGRESS, CLOSED) [required]



Setup:
Before first use this application requires setup and configuration

As this application uses Google Sheets for storage it requires google account.

Configurations:

    i) Spreadsheet Id
        1) Copy attached spreadsheet file foundin src/main/resources/issueTracker.ods to Google drive
            that is going to be used for storage. 
        2) Open the file and copy the Spreadsheet Id from URl. 
            The Spreadsheet ID is the long sequence of characters located between "/d/" and "/edit".
            For example, in a URL like https://docs.google.com/spreadsheets/d/1234567890abcdefg/edit,
            the Spreadsheet ID is 1234567890abcdefg
        3) Place the Spreadsheet Id to application.properties configuration google.spreadsheetId
    ii) Google Client Id & Client Secret
        1) To give access to Google cloud, Google Client Id & Client Secret have to be populated to
            googleApiSecret.json found in src/main/resources/
        2) To get a Google Client ID and Client Secret, follow these steps:
            a) Go to the Google Cloud Platform Console and log in with your Google account.
            b) Create a new project.
            c) Navigate to the "OAuth consent screen" and click get started and fill in the required information.
            d) Then go to the "Clients" section and click "Create client."
            f) Choose "Desktop application" as the Application type and enter the name of your OAuth 2.0 client.
            g) Click the "Create" button to generate your Client ID and Client Secret.



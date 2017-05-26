package com.es.wfx.analytics.ga;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.AnalyticsCriteria;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;

@Service
public class GoogleAnalyticsAdaptor {
	@Value("${application.name}")
	private String APPLICATION_NAME;
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	@Value("${account.email}")
	private String SERVICE_ACCOUNT_EMAIL;
	@Value("${key.file.location}")
	private String KEY_FILE_LOCATION;
	@Value("${view.id}")
	private String VIEW_ID;
	private static final Logger log = LoggerFactory.getLogger(GoogleAnalyticsAdaptor.class);

	private AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY).setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
				.setServiceAccountScopes(AnalyticsReportingScopes.all()).build();

		// Construct the Analytics Reporting service object.
		return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public GetReportsResponse getReport(AnalyticsCriteria criteria) throws IOException, GeneralSecurityException {

		AnalyticsReporting service = initializeAnalyticsReporting();

		// Create the ReportRequest object.
		ReportRequest request = new ReportRequest().setPageToken("1")
				// .setPageSize(5000)
				.setViewId(VIEW_ID).setDateRanges(Arrays.asList(criteria.getRange()))
				.setDimensions(criteria.getDimensions()).setMetrics(criteria.getMetrics());

		ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
		requests.add(request);

		// Create the GetReportsRequest object.
		GetReportsRequest getReport = new GetReportsRequest().setReportRequests(requests);

		// Call the batchGet method.
		GetReportsResponse response = service.reports().batchGet(getReport).execute();
		log.info(" GA data from " + criteria.getRange().getStartDate() + " to " + criteria.getRange().getEndDate());
		// Return the response.
		return response;

	}

}

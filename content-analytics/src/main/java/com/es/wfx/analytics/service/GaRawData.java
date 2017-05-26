package com.es.wfx.analytics.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.AnalyticsCriteria;
import com.es.wfx.analytics.ga.GoogleAnalyticsAdaptor;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;

@Service
public abstract class GaRawData {

	@Autowired
	protected GoogleAnalyticsAdaptor gaAdaptor;

	public int PullAndPersistGAData(DateRange range) throws IOException, GeneralSecurityException{
		AnalyticsCriteria analyticsCriteria=GetGaCriteria(range);
		GetReportsResponse reports = gaAdaptor.getReport(analyticsCriteria);
		return PersistGaRawData(reports);
	}
	protected abstract AnalyticsCriteria GetGaCriteria(DateRange range);
	protected abstract int PersistGaRawData(GetReportsResponse reports);
}

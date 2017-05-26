package com.es.wfx.analytics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.AnalyticsCriteria;
import com.es.wfx.analytics.bean.GaRawTechGeoData;
import com.es.wfx.analytics.dao.GaDAO;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

@Service
public class GaRawTechGeoDataService extends GaRawData {

	@Autowired
	private GaDAO gaDAO;
	
	@Override
	protected AnalyticsCriteria GetGaCriteria(DateRange range) {
		AnalyticsCriteria analyticsCriteria = new AnalyticsCriteria();
		analyticsCriteria.setRange(range);
		List<Metric> metrics = new ArrayList<Metric>();
		
				
		// Set matrix for UniqueEvents
		metrics.add(new Metric().setExpression("ga:uniqueEvents").setAlias("sessions"));
		analyticsCriteria.setMetrics(metrics);

		// Create the Dimensions object.
		List<Dimension> dimensions = new ArrayList<Dimension>();
		dimensions.add(new Dimension().setName("ga:dimension4"));
		dimensions.add(new Dimension().setName("ga:browser"));
		dimensions.add(new Dimension().setName("ga:operatingSystem"));
		dimensions.add(new Dimension().setName("ga:deviceCategory"));
		dimensions.add(new Dimension().setName("ga:country"));
		dimensions.add(new Dimension().setName("ga:language"));
		
		analyticsCriteria.setDimensions(dimensions);

		return analyticsCriteria;
	}

	@Override
	protected int PersistGaRawData(GetReportsResponse reports) {
		List<ReportRow> rows = null;
		for (Report report : reports.getReports()) {
			rows = report.getData().getRows();
			if (rows == null) {
				return 0;
			}
			persistNewReportsToDB(rows);
		}
		return rows.size();
	}
	
	private void persistNewReportsToDB(List<ReportRow> rows) {

		List<GaRawTechGeoData> datas = new ArrayList<GaRawTechGeoData>();
		GaRawTechGeoData data=null;
		
		for (ReportRow row : rows) {
			data = new GaRawTechGeoData();
			List<String> dimensions = row.getDimensions();
			
	    	data.setCorrelationID(dimensions.get(0));
			data.setBrowser(dimensions.get(1));
			data.setOperatingSystem(dimensions.get(2));
			data.setDeviceCategory(dimensions.get(3));
			data.setCountry(dimensions.get(4));
			data.setLanguage(dimensions.get(5));
			datas.add(data);
		}
		gaDAO.insert(datas, GaRawTechGeoData.class);	
	}

}

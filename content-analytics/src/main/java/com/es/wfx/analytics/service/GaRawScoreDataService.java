package com.es.wfx.analytics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.AnalyticsCriteria;
import com.es.wfx.analytics.bean.GaRawScoreData;
import com.es.wfx.analytics.dao.GaDAO;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;

@Service
public class GaRawScoreDataService extends GaRawData {

	@Autowired
	private GaDAO gaDAO;
	
	@Override
	protected AnalyticsCriteria GetGaCriteria(DateRange range) {
		AnalyticsCriteria analyticsCriteria = new AnalyticsCriteria();
		analyticsCriteria.setRange(range);
		List<Metric> metrics = new ArrayList<Metric>();
		
				
		// Set matrix for UniqueEvents
		metrics.add(new Metric().setExpression("ga:uniqueEvents").setAlias("sessions"));
		metrics.add(new Metric().setExpression("ga:eventValue"));
		analyticsCriteria.setMetrics(metrics);

		// Create the Dimensions object.
		List<Dimension> dimensions = new ArrayList<Dimension>();
		dimensions.add(new Dimension().setName("ga:eventCategory"));
		dimensions.add(new Dimension().setName("ga:dimension1"));
	dimensions.add(new Dimension().setName("ga:dimension4"));
		
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

		List<GaRawScoreData> datas = new ArrayList<GaRawScoreData>();
		GaRawScoreData data=null;
		
		for (ReportRow row : rows) {
			data = new GaRawScoreData();
			List<String> dimensions = row.getDimensions();
			List<DateRangeValues> matrics=row.getMetrics();
			
			data.setTitle(dimensions.get(0));
    		data.setUserId(dimensions.get(1));
		    data.setCorrelationID(dimensions.get(2));
//			data.setScore((Integer.parseInt(((DateRangeValues)matrics.get(0)).getValues().get(1).toString())));
			datas.add(data);
		}
		gaDAO.insert(datas, GaRawScoreData.class);	
	}

}

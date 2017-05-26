package com.es.wfx.analytics.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.AnalyticsCriteria;
import com.es.wfx.analytics.bean.GaRawEventData;
import com.es.wfx.analytics.bean.WidgetData;
import com.es.wfx.analytics.dao.GaDAO;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.Dimension;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import com.google.gson.Gson;

@Service
public class GaRawEventDataService extends GaRawData {
	
	private static final Logger log = LoggerFactory.getLogger(GaRawEventDataService.class);
	
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
		dimensions.add(new Dimension().setName("ga:eventCategory"));
		dimensions.add(new Dimension().setName("ga:eventAction"));
		dimensions.add(new Dimension().setName("ga:dimension1"));
		dimensions.add(new Dimension().setName("ga:dimension2"));
		dimensions.add(new Dimension().setName("ga:dimension3"));
		dimensions.add(new Dimension().setName("ga:dimension4"));
		dimensions.add(new Dimension().setName("ga:eventLabel"));
		
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

		List<GaRawEventData> datas = new ArrayList<GaRawEventData>();
		Gson gson = new Gson();
		GaRawEventData data=null;
		
		for (ReportRow row : rows) {
			data = new GaRawEventData();
			List<String> dimensions = row.getDimensions();
			
			data.setTitle(dimensions.get(0));
			data.setEventType(dimensions.get(1));
			data.setUserId(dimensions.get(2));
			data.setCourse(dimensions.get(3));
			data.setChapter(dimensions.get(4));
			data.setCorrelationID(dimensions.get(5));
			try{
				data.setWidgetData(gson.fromJson(dimensions.get(6), WidgetData.class));
			}catch(Exception e){
				log.error(e.getMessage());
			}
			datas.add(data);
			
		}
		gaDAO.insert(datas, GaRawEventData.class);
	}

}

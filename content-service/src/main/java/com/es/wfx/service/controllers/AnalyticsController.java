package com.es.wfx.service.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.wfx.analytics.bean.GaRawEventData;
import com.es.wfx.analytics.bean.MostCompletedCount;
import com.es.wfx.analytics.bean.MostInteractedCount;
import com.es.wfx.analytics.bean.Sample;
import com.es.wfx.analytics.bean.WidgetLevelCompleted;
import com.es.wfx.analytics.bean.WidgetLevelCount;
import com.es.wfx.analytics.reports.InsightsService;
import com.mongodb.DBObject;

@RestController
public class AnalyticsController {

	@Autowired
	InsightsService insightsService;

	/**
	 * Gets all the insights for report 
	 * @param id : Insights name (example: MostPopular)
	 * @return List of List of respective DBObject depending on "id" parameter  
	 * @throws Exception
	 */
	@RequestMapping("/analytics/{id}")
	public List<DBObject> getAllInsights(@PathVariable("id") String id) throws Exception {
		return insightsService.GetAllInsights(id);
	}
	/**
	 * Gets the list of aggregated nodes details for given widget and level
	 * @param title : title of widget
	 * @param level : level of widget
	 * @return List of aggregated nodes details
	 * @throws Exception
	 */
	@RequestMapping("/analytics/widgets/{title}/levels/{level}")
	public List<WidgetLevelCount> getWidgetLevels(@PathVariable("title") String title, @PathVariable("level") String level) throws Exception {
		return insightsService.getWidgetLevels(title,level);
	}
	
	/**
	 * Gets widgets info and total user sessions
	 * @param collectionName : mongo collection
	 * @return Dictionary object of widgets info
	 * @throws Exception
	 */
	@RequestMapping("/widgets/{collectionName}")
	public Map<String, Integer> getWidgetsInfo(@PathVariable("collectionName") String collectionName) throws Exception {
		
		return insightsService.getWidgetsInfo(collectionName);
		
	}
	
	@RequestMapping("/analytics/widgetlevel/{title}")
	public List<WidgetLevelCompleted> getwigetLevelInfo(@PathVariable("title") String title) throws Exception {
		return insightsService.getwigetLevelInfo(title);
	}
}

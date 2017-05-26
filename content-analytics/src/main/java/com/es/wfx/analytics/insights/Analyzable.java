package com.es.wfx.analytics.insights;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.es.wfx.analytics.bean.InsightsSettings;
import com.es.wfx.analytics.bean.WidgetRegistry;
import com.es.wfx.analytics.bean.WidgetSetting;
import com.es.wfx.analytics.dao.MongoHelper;
import com.es.wfx.analytics.service.SettingService;
import com.es.wfx.analytics.service.WidgetRegistryService;

/**
 * Abstract class for performing analytics and providing insights for various
 * parameters. Individual classes responsible for generating analytics must
 * derive from this class.
 * 
 * @author PrashantAcharya
 *
 */
public abstract class Analyzable {

	@Autowired
	WidgetRegistryService widgetRegistryService;

	@Autowired
	SettingService settingService;
	/**
	 * Represents Data Access Object
	 */
	@Autowired
	protected MongoHelper dao;

	/*
	 * public Analyzable() { InsightsMaster insightsMaster=new InsightsMaster();
	 * insightsMaster.setTitle(this.insightsTitle());
	 * insightsMaster.setClassName(this.getClass().getName());
	 * dao.save(insightsMaster); }
	 */

	/**
	 * Next element in the chain of responsibility for the given analytics
	 */
	protected Analyzable next;

	/**
	 * Analyzes data for the current analysis and stores in aggregate collection
	 * in database
	 */
	public void analyze() {

		this.insert(this.getAnalyzedData(), this.beanType());

		if (next != null) {
			next.analyze();
		}
	}

	/**
	 * @return List of aggregated data for the current analysis
	 */
	protected abstract <T> List<T> getAnalyzedData();

	/**
	 * Gets list of insights for the given Widget title
	 * 
	 * @param title
	 *            Title of the widget for which insights is required
	 * @return updated list of insights for the given widget
	 */
	public List<String> insights(String title) {
		List<String> insights = new ArrayList<String>();

		WidgetRegistry widgetRegistry = widgetRegistryService.getWidgetByTitle(title);
		WidgetSetting widgetSetting = settingService.getSettings();

		boolean insightsStatus = false;
		for (InsightsSettings insightsSettings : widgetSetting.getInsightsSettings()) {
			if (insightsSettings.getClassName().equalsIgnoreCase(this.getClass().getName())) {
				insightsStatus = insightsSettings.isSupressed();
			}
		}

		if ((widgetRegistry == null || !widgetRegistry.isInsightStatus()) && !insightsStatus) {
			if (widgetSetting.getGeneralSettings() != null) {
				insights = getInsights(title, widgetSetting.getGeneralSettings().getThreshold());
			}
		}

		if (this.next == null) {
			return insights;
		} else {
			insights.addAll(this.next.insights(title));
			return insights;
		}

	}

	/**
	 * Gets list of insights for the given Widget title
	 * 
	 * @param title
	 *            Title of the widget for which insight is to be generated
	 * @return gets list of insights available for the widget
	 */
	protected abstract List<String> getInsights(String title, int threshold);

	/**
	 * @return type of Bean to be used for the given analytics/insights
	 */
	protected abstract <T> T beanType();

	/**
	 * Inserts data to database for the given bean type
	 * 
	 * @param result
	 *            Data that needs to be stored to the database
	 * @param T
	 *            Type of bean to be used for serializing data to the database
	 */
	protected <T> void insert(List<T> result, Class<?> T) {
		dao.insert(result, T);
	}

	/**
	 * Deletes all data from database for the bean type of the analytics object
	 */
	public void delete() {
		dao.delete(this.beanType());
	}

	/**
	 * Sets next {@link #Analyzable()} object for the chain of responsibilities
	 * 
	 * @param next
	 */
	public void setNext(Analyzable next) {
		this.next = next;
	}

}

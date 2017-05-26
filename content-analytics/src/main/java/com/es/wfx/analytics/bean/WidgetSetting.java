package com.es.wfx.analytics.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mansoora.tm
 *
 */
public class WidgetSetting {
	private String _id;
	private GeneralSettings generalSettings=new GeneralSettings();
	private List<InsightsSettings> insightsSettings = new ArrayList<InsightsSettings>();

	

	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * @param _id
	 *            the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}

	/**
	 * @return the insightsSettings
	 */
	public List<InsightsSettings> getInsightsSettings() {
		return insightsSettings;
	}

	/**
	 * @param insightsSettings
	 *            the insightsSettings to set
	 */
	public void setInsightsSettings(List<InsightsSettings> insightsSettings) {
		this.insightsSettings = insightsSettings;
	}

	/**
	 * @return the generalSettings
	 */
	public GeneralSettings getGeneralSettings() {
		return generalSettings;
	}

	/**
	 * @param generalSettings the generalSettings to set
	 */
	public void setGeneralSettings(GeneralSettings generalSettings) {
		this.generalSettings = generalSettings;
	}
}

/**
 * 
 */
package com.es.wfx.analytics.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.GeneralSettings;
import com.es.wfx.analytics.bean.InsightsSettings;
import com.es.wfx.analytics.bean.WidgetSetting;
import com.es.wfx.analytics.dao.MongoHelper;

/**
 * @author mansoora.tm
 *
 */
@Service
public class SettingService {
	@Autowired
	MongoHelper mongoHelper;

	/**
	 * @param widgetSetting
	 *            data that needs to be stored in database
	 */
	public void saveSetting(WidgetSetting widgetSetting) {
		mongoHelper.save(widgetSetting.getGeneralSettings());
		
		for(InsightsSettings insightsSettings:widgetSetting.getInsightsSettings()){
			mongoHelper.save(insightsSettings);	
		}
	}


	/**
	 * @return get list of setting data
	 */
	public <T> WidgetSetting getSettings() {

		WidgetSetting widgetSetting=new WidgetSetting();
		
		
		List<GeneralSettings> generalSettingsList= mongoHelper.findAll(GeneralSettings.class);
		List<InsightsSettings> insightsSettingsList= mongoHelper.findAll(InsightsSettings.class);
		
		if(generalSettingsList.size()>0){
			widgetSetting.setGeneralSettings(generalSettingsList.get(0));
		}
		
		widgetSetting.setInsightsSettings(insightsSettingsList); 
		return widgetSetting;
	}
}

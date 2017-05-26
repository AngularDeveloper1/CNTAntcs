package com.es.wfx.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.es.wfx.analytics.bean.WidgetSetting;
import com.es.wfx.analytics.service.SettingService;

/**
 * @author mansoora.tm
 *
 */
@RestController
public class SettingController {
	@Autowired
	SettingService settingService;

	/**
	 * @param  widgetSetting data that needs to be stored in the database
	 */
	@RequestMapping(value = "/registry/setting", method = RequestMethod.POST)
	public void widgetSetting(@RequestBody WidgetSetting widgetSetting) {
		settingService.saveSetting(widgetSetting);

	}

	/**
	 * @return get list of setting data
	 * @throws Exception
	 */
	@RequestMapping(value = "/registry/setting", method = RequestMethod.GET)
	public WidgetSetting widgetSetting() throws Exception {
		return settingService.getSettings();
	}

}

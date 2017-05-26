package com.es.wfx.service.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.es.wfx.analytics.bean.WidgetRegistry;
import com.es.wfx.analytics.service.WidgetRegistryService;

/**
 * @author mansoora.tm
 *
 */
@RestController
public class RegistryController {
	@Autowired
	WidgetRegistryService widgetRegistryService;

	/**
	 * @param widgetRegistry
	 *            data that needs to be stored in the database
	 * @throws Exception
	 *             to post the widget information
	 */
	@RequestMapping(value = "/registry/widget", method = RequestMethod.POST)
	public void widgetRegistery(@RequestBody WidgetRegistry widgetRegistry) {
		widgetRegistryService.saveWidget(widgetRegistry);

	}

	/**
	 * @return
	 * @throws Exception
	 *             to get All widgets information
	 */
	@RequestMapping(value = "/registry/widget", method = RequestMethod.GET)
	public List<WidgetRegistry> widgetRegistery() throws Exception {
		return widgetRegistryService.getWidget(WidgetRegistry.class);

	}

	/**
	 * @param widgetRegistry
	 * @param id
	 * @throws Exception
	 *             to update widget information
	 */
	@RequestMapping(value = "/registry/widget", method = RequestMethod.PUT)
	public void updateWidget(@RequestBody WidgetRegistry widgetRegistry) {

		widgetRegistryService.updateWidget(widgetRegistry);
	}

	/**
	 * @param id
	 * @throws Exception
	 *             to delete specific widget information for the given id
	 */

	@RequestMapping(value = "/registry/widget/{id}", method = RequestMethod.DELETE)
	public void widgetRegistery(@PathVariable("id") String id) throws Exception {
		widgetRegistryService.deleteWidget(id);

	}

}

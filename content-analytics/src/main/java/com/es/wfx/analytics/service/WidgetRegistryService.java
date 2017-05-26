/**
 * 
 */
package com.es.wfx.analytics.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.es.wfx.analytics.bean.WidgetRegistry;
import com.es.wfx.analytics.dao.MongoHelper;
import com.es.wfx.analytics.exception.BadDataException;

/**
 * @author mansoora.tm
 * @param <T>
 *
 */
@Service
public class WidgetRegistryService {
	@Autowired
	MongoHelper mongoHelper;

	/**
	 * @param widgetRegistry
	 *            save widget information to mongo db
	 */

	public void saveWidget(WidgetRegistry widgetRegistry) {
		this.validate(widgetRegistry);
		mongoHelper.save(widgetRegistry);

	}

	/**
	 * @param entityClass
	 * @return get list of widget information from mongo db
	 */
	public <T> List<T> getWidget(Class<T> entityClass) {
		return mongoHelper.findAll(entityClass);

	}

	/**
	 * @param widgetRegistry
	 *            update widget information to mongo db
	 */
	public <T> void updateWidget(WidgetRegistry widgetRegistry) {

		this.validate(widgetRegistry);
		mongoHelper.save(widgetRegistry);

	}

	/**
	 * @param id
	 *            delete specific widget information from mongo db for the given
	 *            id
	 */
	public void deleteWidget(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

		mongoHelper.remove(query, WidgetRegistry.class);

	}

	/**
	 * @param title of the widget for which widget is required
	 * @return widget information
	 */
	public WidgetRegistry getWidgetByTitle(String title) {
		Query query = new Query();
		query.addCriteria(Criteria.where("title").is(title));
		return mongoHelper.findOne(query, WidgetRegistry.class);

	}

	private void validate(WidgetRegistry widgetRegistry) {
		if (widgetRegistry.getTitle().isEmpty() || widgetRegistry.getTitle() == null) {
			throw new BadDataException("Title is empty or null");
		} else if (widgetRegistry.getTags().isEmpty() || widgetRegistry.getClass() == null) {
			throw new BadDataException("Tags is empty or null");
		} else if (widgetRegistry.getUrl().isEmpty() || widgetRegistry.getUrl() == null) {
			throw new BadDataException("Url is empty or null");
		}

	}
}

package com.es.wfx.analytics.reports;

import static org.mockito.Matchers.matches;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.GaRawEventData;
import com.es.wfx.analytics.bean.MostCompletedCount;
import com.es.wfx.analytics.bean.MostInteractedCount;
import com.es.wfx.analytics.bean.Sample;
import com.es.wfx.analytics.bean.WidgetLevelCompleted;
import com.es.wfx.analytics.bean.WidgetLevelCount;
import com.es.wfx.analytics.dao.CustomGroupOperation;
import com.es.wfx.analytics.dao.MongoHelper;
import com.jayway.jsonpath.internal.function.numeric.Max;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Service
public class InsightsService {

	@Autowired
	private MongoHelper mongoHelper;

	public List<DBObject> GetAllInsights(String id) throws IOException {
		String colName = getCollectionName(id);
		return mongoHelper.getCollection(colName).find().toArray();
	}
	
	public List<WidgetLevelCount> getWidgetLevels(String title,String level){
		Query query = new Query();
		query.addCriteria(Criteria.where("title").is(title));
		query.addCriteria(Criteria.where("level").is(level));
		return mongoHelper.find(query,WidgetLevelCount.class);
	}
	
	public Map<String, Integer> getWidgetsInfo(String collectionName) throws IOException {
		String collection = getCollectionName(collectionName);

		Map<String, Integer> widgetInfo = new Hashtable<String, Integer>();
		DBCollection db = (DBCollection) mongoHelper.getCollection(collection);

		widgetInfo.put("totalWidgets",db.distinct( "title" ).size());
		widgetInfo.put("totalUserSessions", db.distinct( "correlationID" ).size());

		return widgetInfo;

	}
	
	private String getCollectionName(String id) throws IOException {
		Properties props = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mappings.properties");
		props.load(inputStream);
		return props.getProperty(id);
	}

	public List<WidgetLevelCompleted> getwigetLevelInfo(String title) {
		
		Aggregation agg = newAggregation(
				match(Criteria.where("title").is(title).and("eventType").is("interact").and("widgetData.node").is("next")),
				group("correlationID").max("widgetData.level").as("maxlevel"),
				project("maxlevel").and("correlationId").previousOperation());
		//Convert the aggregation result into a List
		AggregationResults<WidgetLevelCompleted> groupResults
			= mongoHelper.aggregate(agg, GaRawEventData.class, WidgetLevelCompleted.class);
		List<WidgetLevelCompleted> result = groupResults.getMappedResults();

		return result;
		
	
		
	}

}
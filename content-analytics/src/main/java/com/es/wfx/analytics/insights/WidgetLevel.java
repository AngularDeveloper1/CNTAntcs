package com.es.wfx.analytics.insights;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.List;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import com.es.wfx.analytics.bean.GaRawEventData;
import com.es.wfx.analytics.bean.WidgetLevelCount;

public class WidgetLevel extends Analyzable  {

	@SuppressWarnings("unchecked")
	@Override
	protected List<WidgetLevelCount> getAnalyzedData() {
		this.delete();
		
		Aggregation agg = newAggregation(
				match(Criteria.where("eventType").is("interact").and("widgetData.level").exists(true)),
				group("title","widgetData.level","widgetData.node").count().as("total"),
				project("total").andInclude("title").andInclude("widgetData.level").andInclude("widgetData.node"));

		AggregationResults<WidgetLevelCount> groupResults = dao.aggregate(agg, GaRawEventData.class, WidgetLevelCount.class);
		return groupResults.getMappedResults();
	}

	@Override
	protected List<String> getInsights(String title,int threshould) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<WidgetLevelCount> beanType() {
		return WidgetLevelCount.class;
	}
	
	

}

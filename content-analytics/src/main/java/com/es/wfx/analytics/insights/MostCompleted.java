package com.es.wfx.analytics.insights;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.es.wfx.analytics.bean.GaRawEventData;
import com.es.wfx.analytics.bean.MostCompletedCount;
import com.es.wfx.analytics.dao.CustomGroupOperation;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class MostCompleted extends Analyzable {

	@SuppressWarnings("unchecked")
	@Override
	protected List<MostCompletedCount> getAnalyzedData() {
		this.delete();
		
		BasicDBList basicDBList = new BasicDBList();
		basicDBList.add("$widgetData.level");
		basicDBList.add("$widgetData.MaxDepth");

		Aggregation aggCount2 = newAggregation(
				new CustomGroupOperation(new BasicDBObject("$project",new BasicDBObject("areEqual", new BasicDBObject("$eq", basicDBList)).append("total", 1).append("title", 1).append("widgetData",1).append("eventType", 1))),
				match(Criteria.where("areEqual").is(true).and("widgetData.level").exists(true).and("eventType").is("interact")),
		        group("title").count().as("total"),
		        sort(Sort.Direction.DESC, "total")
		        );
				
		return dao.aggregate(aggCount2, GaRawEventData.class, MostCompletedCount.class).getMappedResults();
	}

	@Override
	protected List<String> getInsights(String title, int threshold) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class<MostCompletedCount> beanType() {
		return MostCompletedCount.class;
	}

}

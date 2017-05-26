/**
 * 
 */
package com.es.wfx.analytics.insights;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import com.es.wfx.analytics.bean.GaRawEventData;
import com.es.wfx.analytics.bean.MostPopularCount;

/**
 * Performs analysis to generate data for "Most Popular" widgets from data
 * captured in Analytics service
 * 
 * @author PrashantAcharya
 *
 */
public class MostPopular extends Analyzable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.es.wfx.analytics.insights.Analyzable#getAnalyzedData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@CacheEvict(value = "mostpopular", allEntries = true)
	protected List<MostPopularCount> getAnalyzedData() {

		// delete existing aggregate data
		this.delete();

		// Aggregate data based on given criteria
		Aggregation agg = newAggregation(match(Criteria.where("eventType").is("init")),
				group("title").count().as("total"), project("total").and("title").previousOperation(),
				sort(Sort.Direction.DESC, "total"));

		AggregationResults<MostPopularCount> groupResults = dao.aggregate(agg, GaRawEventData.class,
				MostPopularCount.class);
		return groupResults.getMappedResults();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.es.wfx.analytics.insights.Analyzable#getInsights(java.lang.String)
	 */
	@Override
	@Cacheable(value = "mostpopular", key = "#title")
	protected List<String> getInsights(String title, int threshold) {

		List<String> insights = new ArrayList<String>();
		List<MostPopularCount> mostPopular = new ArrayList<MostPopularCount>();
	
		mostPopular = dao.findAll(MostPopularCount.class);

		if (mostPopular.get(0).getTitle().equalsIgnoreCase(title) && mostPopular.get(0).getTotal() >= threshold) {
			insights.add(
					"This is the <span class='tHlg'>most popular</span> widget in the course. Something not to miss!!");
		} else if (mostPopular.get(1).getTitle().equalsIgnoreCase(title)
				&& mostPopular.get(1).getTotal() >= threshold) {
			insights.add(
					"This is the <span class='tHlg'>second most popular</span> widget in the course. Something not to miss!!");
		} else if (mostPopular.get(2).getTitle().equalsIgnoreCase(title)
				&& mostPopular.get(2).getTotal() >= threshold) {
			insights.add(
					"This is the <span class='tHlg'>third most popular</span> widget in the course. Something not to miss!!");
		}
	
		return insights;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.es.wfx.analytics.insights.Analyzable#beanType()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Class<MostPopularCount> beanType() {
		return MostPopularCount.class;
	}
}

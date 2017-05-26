package com.es.wfx.analytics.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCollection;

@Repository
public class MongoHelper {

	@Autowired
	private MongoTemplate repo;

	public void insert(Collection<? extends Object> batchToSave, Class<?> entityClass) {
		repo.insert(batchToSave, entityClass);

	}

	public void save(Object objectToSave) {
		repo.save(objectToSave);
	}

	public <T> void delete(Class<T> entityClass) {
		repo.dropCollection(entityClass);
	}

	public <T> AggregationResults<T> aggregate(Aggregation aggregation, Class<?> inputType, Class<T> outputType) {
		return repo.aggregate(aggregation, inputType, outputType);
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return repo.findAll(entityClass);
	}
	
	public <T> List<T> find(Query query, Class<T> entityClass) {
		return repo.find(query,entityClass);
	}

	public <T> T findOne(Query query, Class<T> entityClass) {
		return repo.findOne(query, entityClass);
	}

	public <T> void update(Query query, Update update, Class<T> entityClass) {
		repo.findAndModify(query, update, entityClass);
	}

	public <T> void remove(Query query, Class<T> entityClass) {
		repo.remove(query, entityClass);

	}
	
	public <T> GroupByResults<T> group(String inputCollectionName, GroupBy groupBy, Class<T> entityClass) {
		return repo.group(inputCollectionName, groupBy, entityClass);

	}
	
	public DBCollection getCollection(String collectionName) {
		return repo.getCollection(collectionName);
	}

}

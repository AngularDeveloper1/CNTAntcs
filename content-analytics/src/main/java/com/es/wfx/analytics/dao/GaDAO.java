package com.es.wfx.analytics.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.es.wfx.analytics.bean.GaDataPullLog;

@Repository
public class GaDAO {
	
	@Autowired
	private MongoHelper mongoHelper;
	
	public void insert(Collection<? extends Object> batchToSave, Class<?> entityClass){
		mongoHelper.insert(batchToSave, entityClass);
	}
	
	public void save(Object objectToSave){
		mongoHelper.save(objectToSave);
	}

	public Date lastGADataPulledDate(){
		Query query = new Query();
		query.addCriteria(Criteria.where("status").ne(2));
		query.with(new Sort(Sort.Direction.DESC, "since"));
		
		GaDataPullLog pullLog = mongoHelper.findOne(query, GaDataPullLog.class);
		
		if(pullLog != null){
			return pullLog.getSince();	
		}else{
			return null;
		}
		
	}
}

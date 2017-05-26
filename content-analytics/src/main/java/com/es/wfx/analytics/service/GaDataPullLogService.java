/**
 * 
 */
package com.es.wfx.analytics.service;

import java.text.ParseException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.es.wfx.analytics.bean.GaDataPullLog;
import com.es.wfx.analytics.dao.GaDAO;
import com.es.wfx.analytics.utility.Utility;
import com.google.api.services.analyticsreporting.v4.model.DateRange;

/**
 * @author mansoora.tm
 *
 */
@Service
public class GaDataPullLogService {

	private static final Logger log = LoggerFactory.getLogger(GaDataPullLogService.class);
	
	@Autowired
	private GaDAO gaDAO;

	@Value("${start.date}")
	private String initialStartDate;
	
	@Value("${ga.latency.hours}")
	private int latencyHours;

	GaDataPullLog data = null;

	// when job fired
	public DateRange getDateRange() throws ParseException {
		Date startDate = gaDAO.lastGADataPulledDate();
		//Date endDate = new Date(System.currentTimeMillis() - (latencyHours * 60 * 60 * 1000));
		Date endDate = new Date(System.currentTimeMillis());
		DateRange range = new DateRange();
		if (startDate == null) {
			range.setStartDate(initialStartDate);
		}else{
			range.setStartDate(Utility.getUtcDateTime(startDate));
		}
		range.setEndDate(Utility.getUtcDateTime(endDate));
		data = new GaDataPullLog();
		this.saveLogData(range,0,0);
		return range;
	}

	//when job success
	public void logSuccess(int count) {
		this.saveLogData(null,1,count);
	}

	// when job failed
	public void logFailed()  {
		this.saveLogData(null,2,0);
	}
	
	private void saveLogData(DateRange dateRange,int status, int count){
		try {
			if(dateRange!=null){
				data.setDate(Utility.getUtcDateTime(dateRange.getStartDate()));
				data.setSince(Utility.getUtcDateTime(dateRange.getEndDate()));	
			}
			data.setStatus(status);
			data.setCount(count);
			gaDAO.save(data);
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
	}

}

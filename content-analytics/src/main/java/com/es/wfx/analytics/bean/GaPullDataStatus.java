/**
 * 
 */
package com.es.wfx.analytics.bean;

import com.google.api.services.analyticsreporting.v4.model.DateRange;

/**
 * @author mansoora.tm
 *
 */
public class GaPullDataStatus {
	
	private DateRange range;
	
	private int count;
	
	
	public GaPullDataStatus()
	{
		
	}
	
	
	public GaPullDataStatus(DateRange range, int count) {
		super();
		this.range = range;
		this.count = count;
	}

	/**
	 * @return the range
	 */
	public DateRange getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public void setRange(DateRange range) {
		this.range = range;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	

}

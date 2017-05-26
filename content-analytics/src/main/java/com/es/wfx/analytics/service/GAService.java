package com.es.wfx.analytics.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.es.wfx.analytics.bean.GaPullDataStatus;
import com.google.api.services.analyticsreporting.v4.model.DateRange;

@Service
public class GAService {

	@Autowired
	GaRawDataFactory gaRawDataFactory;
	
	public GaPullDataStatus PullAndPersistGAData(DateRange range) throws IOException, GeneralSecurityException {
		int rows = 0;
		for (GARawDataTypes type : GARawDataTypes.values()) {
			GaRawData gaRawData=gaRawDataFactory.GetObject(type) ;
			if(gaRawData!= null){
				rows+=gaRawData.PullAndPersistGAData(range);	
			}
		}
		return new GaPullDataStatus(range, rows);
	}

}

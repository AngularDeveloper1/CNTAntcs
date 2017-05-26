package com.es.wfx.analytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GaRawDataFactory {
	
	@Autowired
	private GaRawEventDataService gaRawEventData;
	
	@Autowired
	private GaRawScoreDataService gaRawScoreData;
	
	@Autowired
	private GaRawTechGeoDataService gaRawTechGeoData;
	
	public GaRawData GetObject(GARawDataTypes gaRawDataTypes){
		if(gaRawDataTypes==GARawDataTypes.EVENTDATA){
			return gaRawEventData;
		}else if(gaRawDataTypes==GARawDataTypes.SCOREDATA){
			return gaRawScoreData;
		}else if(gaRawDataTypes==GARawDataTypes.TECHGEODATA){
			return gaRawTechGeoData;
		}else{
			return null;	
		}
	}

}

package com.es.wfx.analytics.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import com.es.wfx.analytics.insights.Analyzable;

@Service
public class InsightsFacade {

	@Value("${analyze.process.initial.class.name}")
	private String ANALYZE_PROCESS_INITIAL_CLASS_NAME;
	
	@SuppressWarnings("resource")
	public Analyzable getAnalyzer() {

		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		Analyzable analyzable = (Analyzable) context.getBean(ANALYZE_PROCESS_INITIAL_CLASS_NAME);
		
		return analyzable;

	}

}

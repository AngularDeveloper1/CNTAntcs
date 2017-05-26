package com.es.wfx.analytics;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.es.wfx.analytics.insights.MostInteracted;

@ComponentScan({ "org.springframework.data.mongodb.core", "com.es.wfx.analytics.insights" })

public class Main {

	private static ApplicationContext appCtx;

	public static void main(String[] args) {

		appCtx = new FileSystemXmlApplicationContext("src\\main\\resources\\application-context.xml");
		MostInteracted mi = (MostInteracted) appCtx.getBean("rule1");

		mi.analyze();

		// MostInteracted analyzer = new MostInteracted();
		// analyzer.analyze();

	}

}

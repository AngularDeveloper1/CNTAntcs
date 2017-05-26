package com.es.wfx.service.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan({ "com.es.wfx.analytics" })

public class InsightsController {

	@Autowired
	com.es.wfx.analytics.core.InsightsFacade analytics;

	@RequestMapping("/insights/{title}")
	public List<String> getInsights(@PathVariable("title") String title) throws Exception {

		com.es.wfx.analytics.insights.Analyzable analyzer = analytics.getAnalyzer();

		return analyzer.insights(title);

	}

}

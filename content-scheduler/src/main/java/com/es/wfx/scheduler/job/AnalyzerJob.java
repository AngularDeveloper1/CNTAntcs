package com.es.wfx.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AnalyzerJob implements Job {
	private static final Logger log = LoggerFactory.getLogger(AnalyzerJob.class);

	@Value("${maxretry.count}")
	private int maxretryCount;

	@Autowired
	com.es.wfx.analytics.core.InsightsFacade analytics;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		int retryCount = jobExecutionContext.getRefireCount();
		try {

			com.es.wfx.analytics.insights.Analyzable analyzer = analytics.getAnalyzer();
			analyzer.analyze();

		} catch (Exception ex) {
			if (retryCount < maxretryCount) {
				JobExecutionException qe = new JobExecutionException(ex);
				qe.setRefireImmediately(true);
				throw qe;
			} else {
				log.error(ex.getMessage());
			}
		}

	}

}
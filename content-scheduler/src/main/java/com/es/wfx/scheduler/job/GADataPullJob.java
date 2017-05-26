package com.es.wfx.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.es.wfx.analytics.bean.GaPullDataStatus;
import com.es.wfx.analytics.service.GAService;
import com.es.wfx.analytics.service.GaDataPullLogService;
import com.google.api.services.analyticsreporting.v4.model.DateRange;

public class GADataPullJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(GADataPullJob.class);

	@Autowired
	private GAService gaService;

	@Autowired
	private GaDataPullLogService logService;

	@Value("${maxretry.count}")
	private int maxretryCount;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		int retryCount = jobExecutionContext.getRefireCount();
		try {
			DateRange range = logService.getDateRange();
			GaPullDataStatus status = gaService.PullAndPersistGAData(range);

			logService.logSuccess(status.getCount());
			log.info("Scheduler Job excecuted and pushed to database");

			Scheduler scheduler = jobExecutionContext.getScheduler();
			JobKey jobKey = JobKey.jobKey("analyzerJob", "DEFAULT");
			scheduler.triggerJob(jobKey);

		} catch (Exception ex) {
			logService.logFailed();
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

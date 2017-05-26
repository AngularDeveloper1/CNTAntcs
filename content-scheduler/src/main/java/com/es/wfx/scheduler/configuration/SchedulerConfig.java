package com.es.wfx.scheduler.configuration;

import liquibase.integration.spring.SpringLiquibase;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import com.es.wfx.scheduler.job.AnalyzerJob;
import com.es.wfx.scheduler.job.GADataPullJob;

import java.io.IOException;
import java.util.Properties;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class SchedulerConfig {
	
	@Autowired
	private Environment environment;
	
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext, SpringLiquibase springLiquibase)
    {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,@Qualifier("cronJobTrigger") Trigger cronJobTrigger) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(cronJobTrigger);
        factory.setJobDetails(analyzerJob().getObject());
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(getQuartzConfigurationFile()));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
    
    private String getQuartzConfigurationFile(){
    	String[] environment=this.environment.getActiveProfiles();
    	StringBuilder quartzPropertiesFile=new StringBuilder("/");
    	if(environment.length>0){
    		if(environment[0].equals("dev")){
    			quartzPropertiesFile.append("quartz-dev.properties");
    		}else if(environment[0].equals("stg")){
    			quartzPropertiesFile.append("quartz-stg.properties");
    		}else if(environment[0].equals("prd")){
    			quartzPropertiesFile.append("quartz-prd.properties");
    		}
    	}else{
    		quartzPropertiesFile.append("quartz.properties");
    	}
    	return quartzPropertiesFile.toString();
    }

    @Bean
    public JobDetailFactoryBean rawDataJob() {
        return createJobDetail(GADataPullJob.class);
        
    }

    @Bean
	public JobDetailFactoryBean analyzerJob() {
		return createJobDetail(AnalyzerJob.class);
		
	}
        
    @Bean(name = "simpleJobTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("rawDataJob") JobDetail jobDetail,
                                                     @Value("${simplejob.frequency}") long frequency) {
        return createTrigger(jobDetail, frequency);
    }
    
    @Bean(name = "cronJobTrigger")
    public CronTriggerFactoryBean cronJobTrigger(@Qualifier("rawDataJob") JobDetail jobDetail,
                                                     @Value("${cronjob.frequency}") String frequency) {
        return createCronTrigger(jobDetail, frequency);
    }

    private static <T extends Job> JobDetailFactoryBean createJobDetail(Class<T> jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    private static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    private static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        return factoryBean;
    }

}

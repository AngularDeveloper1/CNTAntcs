package com.es.wfx.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import com.es.wfx.scheduler.configuration.SchedulerConfig;

@SpringBootApplication
@Import({ SchedulerConfig.class })

@ComponentScan({ "com.es.wfx.analytics" })

public class WfxSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WfxSchedulerApplication.class, args);
	}
}

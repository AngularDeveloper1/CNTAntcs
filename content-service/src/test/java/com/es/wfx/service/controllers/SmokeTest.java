/**
 * 
 */
package com.es.wfx.service.controllers;

/**
 * @author mansoora.tm
 *
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private AnalyticsController analyticsController;

    @Autowired
    private InsightsController insightsController;
    
    @Autowired
    private SettingController settingController;
    
    @Autowired
    private RegistryController registryController;
    
    
    @Test
    public void analyticsLoads() throws Exception {
        assertThat(analyticsController).isNotNull();
    }
    
    @Test
    public void insightsLoads() throws Exception {
        assertThat(insightsController).isNotNull();
    }
    
    @Test
    public void settingLoads() throws Exception {
        assertThat(settingController).isNotNull();
    }
    
    @Test
    public void registryLoads() throws Exception {
        assertThat(registryController).isNotNull();
    }
}
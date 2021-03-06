package com.es.wfx.service.controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mostPopular() throws Exception {
        this.mockMvc.perform(get("/analytics/mp")).andDo(print()).andExpect(status().isOk());
                
    }
    
    @Test
    public void mostInteracted() throws Exception {
        this.mockMvc.perform(get("/analytics/mi")).andDo(print()).andExpect(status().isOk());
                
    }
}
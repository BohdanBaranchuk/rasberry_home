package com.homedev.smart_home.smart89.v1.controllers;


import com.homedev.smart_home.smart89.v1.database.repository.HeatFloorDatabaseModelRepository;
import com.homedev.smart_home.smart89.v1.domain.models.home.Flat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(HeatFloorPageController.class)
public class HeatFloorPageControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    Flat flat;

    @MockBean
    HeatFloorDatabaseModelRepository repository;

    @MockBean
    CounterService counterService;

    @MockBean
    GaugeService gaugeService;

    @Test
    public void tesGetMainFloorPage() throws Exception {
        this.mvc.perform(get("/floor")).andExpect(status().is4xxClientError());
    }
}

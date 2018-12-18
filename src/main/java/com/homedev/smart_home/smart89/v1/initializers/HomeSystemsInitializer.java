package com.homedev.smart_home.smart89.v1.initializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeSystemsInitializer {

    @Autowired
    private HeatFloorSystemsInitializer floorSystemsInitializer;

    public void initializeAllHomeSystems() {

        floorSystemsInitializer.init();
    }
}

package com.homedev.smart_home.smart89.v1.config;

import com.homedev.smart_home.smart89.v1.config.air.AirConfig;
import com.homedev.smart_home.smart89.v1.config.floor.FloorConfig;
import com.homedev.smart_home.smart89.v1.config.floor.FloorsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TestConfig implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(
            TestConfig.class);

    private ApplicationContext ctx;

    @Autowired
    private Environment environment;

    @Autowired
    public TestConfig(
            FloorsConfig floorsConfig,
            AirConfig airConfig) {

        for (FloorConfig floorConfig : floorsConfig.getFloorsConfig()) {

            log.info(
                    "READ FROM FLOOR CONF: {}, {}, {}, {}, {}, {}",
                    floorConfig.getId(),
                    floorConfig.getRoomName(),
                    floorConfig.getValvePinNumber(),
                    floorConfig.getSensorValueFilePath(),
                    floorConfig.getDefaultStartupTemperature(),
                    floorConfig.getDefaultStartupMode());
        }

        log.info(
                "READ FROM AIR CONF: {}, {}, {}, {}, {}, {}",
                airConfig.getId(),
                airConfig.getRoomName(),
                airConfig.getBoilerPinNumber(),
                airConfig.getSensorValueFilePath(),
                airConfig.getDefaultStartupTemperature(),
                airConfig.getDefaultStartupMode());
    }

    public void setApplicationContext(ApplicationContext context) {
        this.ctx = context;
    }
}

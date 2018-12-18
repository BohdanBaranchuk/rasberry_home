package com.homedev.smart_home.smart89.v1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TestConfig implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(
            TestConfig.class);

    private ApplicationContext ctx;

    @Autowired
    public TestConfig(FloorsConfig floorsConfig) {

        for (FloorConfig value : floorsConfig.getFloorsConfig()) {

            log.info(
                    "READ FROM CONF: {}, {}, {}, {}",
                    value.getId(),
                    value.getRoomName(),
                    value.getValvePinNumber(),
                    value.getSensorValueFilePath());
        }
    }

    public void setApplicationContext(ApplicationContext context) {
        this.ctx = context;
    }
}

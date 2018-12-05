package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.mock;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.DiscreteOutput;
import org.apache.log4j.Logger;

public class DiscreteOutputMock implements DiscreteOutput {

    private final Logger logger = Logger.getLogger(DiscreteInputMock.class);

    public void close() {
        logger.info("set output to close level");
    }

    public void open() {
        logger.info("set output to open level");
    }

    public void toggle() {
        logger.info("toggle output");
    }

    public void pulseClose(long pulseTime) {
        logger.info("pulseClose outut for pulseTime: " + pulseTime);
    }
}

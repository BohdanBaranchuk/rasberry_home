package com.homedev.smart_home.smart89.v1.domain.models.output;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.IOPin;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.PinMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputRele extends IOPin {

    private static final Logger log = LoggerFactory.getLogger(OutputRele.class);

    private ReleStatus releStatus;

    public OutputRele(String physicalName) {
        super(physicalName, PinMode.OUTPUT);
    }

    public ReleStatus getReleStatus() {
        return releStatus;
    }

    public void on() {
        log.debug("ON rele");
    }

    public void off() {
        log.debug("OFF rele");
    }

    public void switchOutput() {
        log.debug("SWITCH rele");
    }

    public void impulse(int impulseTime) {
        log.debug("IMPULSE rele");
    }
}

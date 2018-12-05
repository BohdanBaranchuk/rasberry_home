package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.mock;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.Discreteinput;
import org.apache.log4j.Logger;

public class DiscreteInputMock implements Discreteinput {

    private final Logger logger = Logger.getLogger(DiscreteInputMock.class);

    public boolean isClosed() {
        return false;
    }

    public boolean isOpen() {
        return false;
    }
}

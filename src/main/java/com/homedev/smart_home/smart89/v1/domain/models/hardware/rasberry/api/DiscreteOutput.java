package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api;

public interface DiscreteOutput {

    void close();

    void open();

    void toggle();

    void pulseClose(long pulseTime);
}

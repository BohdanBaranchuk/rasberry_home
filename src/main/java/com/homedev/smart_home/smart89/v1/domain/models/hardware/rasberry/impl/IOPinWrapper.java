package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

abstract class IOPinWrapper {

    GpioController gpio = GpioFactory.getInstance();

    void shutdown() {
        gpio.shutdown();
    }
}

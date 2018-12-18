package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.DiscreteOutput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class DiscreteOutputImpl extends IOPinWrapper implements DiscreteOutput {

    private GpioPinDigitalOutput pin;

    public DiscreteOutputImpl(
            int pinNumber,
            String pinHumanDescription,
            PinState initialPinState) {

        Pin raspiPin = RaspiPin.getPinByAddress(pinNumber);

        pin = gpio.provisionDigitalOutputPin(
                raspiPin,
                pinHumanDescription,
                initialPinState);

        pin.setShutdownOptions(
                true,
                PinState.LOW);
    }

    public void close() {
        pin.high();
    }

    public void open() {
        pin.low();
    }

    public void toggle() {
        pin.toggle();
    }

    public void pulseClose(long pulseTime) {

        pin.pulse(
                pulseTime,
                true);
    }
}

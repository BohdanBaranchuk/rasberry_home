package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.Discreteinput;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscreteInputImpl extends IOPinWrapper implements Discreteinput {

    private static final Logger log = LoggerFactory.getLogger(DiscreteInputImpl.class);

    private PinState pinState;

    public DiscreteInputImpl(Pin pinPi4jNumber) {

        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(
                pinPi4jNumber,
                PinPullResistance.PULL_DOWN);

        myButton.setShutdownOptions(true);

        myButton.addListener(new GpioPinListenerDigital() {

            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

                pinState = event.getState();

                log.debug(
                        " --> GPIO PIN STATE CHANGE: " +
                                event.getPin() + " = " + event.getState());
            }
        });
    }

    public boolean isClosed() {
        return pinState.isHigh();
    }

    public boolean isOpen() {
        return pinState.isLow();
    }
}

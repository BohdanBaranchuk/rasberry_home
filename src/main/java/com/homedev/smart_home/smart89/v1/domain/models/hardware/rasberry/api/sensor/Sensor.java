package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.sensor;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.IOPin;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.PinMode;

public abstract class Sensor extends IOPin {

    protected float value;

    public Sensor(
            String sensorValueFilePath) {

        super(sensorValueFilePath, PinMode.INPUT);
    }

    public float getValue() {
        return value;
    }

    public abstract SensorType getType();

    public String toString() {

        return super.toString() +
                "Sensor value: " + value + "\n";
    }
}

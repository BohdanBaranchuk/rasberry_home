package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.sensor;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.IOPin;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.PinMode;

public abstract class Sensor extends IOPin {

    protected float value;

    private long id;

    private String logicalName;

    public Sensor(
            String physicalName,
            long id,
            String logicalName) {

        super(physicalName, PinMode.INPUT);

        this.id = id;
        this.logicalName = logicalName;
    }

    protected long getId() {
        return id;
    }

    protected String getLogicalName() {
        return logicalName;
    }

    public float getValue() {
        return value;
    }

    public abstract SensorType getType();

    public String toString() {

        return super.toString() +
                "Sensor id: " + id + "\n" +
                "Sensor logicalName: " + logicalName + "\n" +
                "Sensor value: " + value + "\n";
    }
}

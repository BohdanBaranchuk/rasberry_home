package com.homedev.smart_home.smart89.v1.domain.models.hardware;

public abstract class IOPin {

    protected String sensorValueFilePath;
    PinMode pinMode;

    public IOPin(
            String sensorValueFilePath,
            PinMode pinMode) {

        this.sensorValueFilePath = sensorValueFilePath;
        this.pinMode = pinMode;
    }

    public String getSensorValueFilePath() {
        return sensorValueFilePath;
    }

    public PinMode getPinMode() {
        return pinMode;
    }

    public String toString() {
        return "sensorValueFilePath: " + sensorValueFilePath + "\n" +
                "pinMode: " + pinMode + "\n";
    }
}

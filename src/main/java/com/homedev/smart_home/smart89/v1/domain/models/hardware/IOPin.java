package com.homedev.smart_home.smart89.v1.domain.models.hardware;

public abstract class IOPin {

    protected String physicalName;
    PinMode pinMode;

    public IOPin(
            String physicalName,
            PinMode pinMode) {

        this.physicalName = physicalName;
        this.pinMode = pinMode;
    }

    public String toString() {
        return "physicalName: " + physicalName + "\n" +
                "pinMode: " + pinMode + "\n";
    }
}

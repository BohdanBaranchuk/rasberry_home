package com.homedev.smart_home.smart89.v1.controllers.front_model;

public class HeatFloorModel {

    private String roomName;

    private float currentTemperature;
    private int desiredTemperature;

    private String modeName;

    public HeatFloorModel(
            String systemName,
            float currentTemperature,
            int desiredTemperature,
            String modeName) {

        this.roomName = systemName;
        this.currentTemperature = currentTemperature;
        this.desiredTemperature = desiredTemperature;
        this.modeName = modeName;
    }

    public String getRoomName() {
        return roomName;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public int getDesiredTemperature() {
        return desiredTemperature;
    }

    public String getModeName() {
        return modeName;
    }

    public String toString() {

        return String.format(
                "roomName: %s\n currentTemperature: %f\n desiredTemperature: %d\n modeName: %s\n",
                roomName,
                currentTemperature,
                desiredTemperature,
                modeName);
    }
}

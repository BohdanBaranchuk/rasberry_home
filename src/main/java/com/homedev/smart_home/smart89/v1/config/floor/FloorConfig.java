package com.homedev.smart_home.smart89.v1.config.floor;

public class FloorConfig {

    private int id;

    private String roomName;

    private String sensorValueFilePath;

    private float defaultStartupTemperature;

    private String defaultStartupMode;

    private int valvePinNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSensorValueFilePath() {
        return sensorValueFilePath;
    }

    public void setSensorValueFilePath(String sensorValueFilePath) {
        this.sensorValueFilePath = sensorValueFilePath;
    }

    public int getValvePinNumber() {
        return valvePinNumber;
    }

    public void setValvePinNumber(int valvePinNumber) {
        this.valvePinNumber = valvePinNumber;
    }

    public float getDefaultStartupTemperature() {
        return defaultStartupTemperature;
    }

    public void setDefaultStartupTemperature(float defaultStartupTemperature) {
        this.defaultStartupTemperature = defaultStartupTemperature;
    }

    public String getDefaultStartupMode() {
        return defaultStartupMode;
    }

    public void setDefaultStartupMode(String defaultStartupMode) {
        this.defaultStartupMode = defaultStartupMode;
    }
}

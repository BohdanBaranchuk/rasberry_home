package com.homedev.smart_home.smart89.v1.config;

public class FloorConfig {

    private int id;

    private String roomName;

    private String sensorValueFilePath;

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
}

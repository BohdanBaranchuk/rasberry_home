package com.homedev.smart_home.smart89.v1.config.floor;

import lombok.Data;

@Data
public class FloorConfig {
    private int id;
    private String roomName;
    private String sensorValueFilePath;
    private float defaultStartupTemperature;
    private String defaultStartupMode;
    private int valvePinNumber;
}

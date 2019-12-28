package com.homedev.smart_home.smart89.v1.config.air;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "smart-home.config.air-config")
public class AirConfig {
    private int id;
    private String roomName;
    private String sensorValueFilePath;
    private float defaultStartupTemperature;
    private String defaultStartupMode;
    private int boilerPinNumber;
}

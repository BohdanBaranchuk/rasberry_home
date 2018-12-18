package com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl.sensor;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.sensor.Sensor;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.sensor.SensorType;
import com.homedev.smart_home.smart89.v1.domain.models.scheduler.ScheduledTask;
import com.homedev.smart_home.smart89.v1.jdk.libs.file_utils.FileUtils;
import com.homedev.smart_home.smart89.v1.jdk.libs.string_utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemperatureSensor extends Sensor implements ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(TemperatureSensor.class);

    private SensorType type = SensorType.TEMPERATURE;

    public TemperatureSensor(
            String sensorValueFilePath) {

        super(sensorValueFilePath);
    }

    public SensorType getType() {
        return type;
    }

    public void performTask() {

        try {
            updateTemperatureFromFile();

        } catch (Exception ex) {

            log.error(
                    "Error read temperature value from file: "
                            + getSensorValueFilePath(), ex);
        }
    }

    private void updateTemperatureFromFile() throws Exception {

        String fileContent = FileUtils.readFileAsStringBuffered(
                sensorValueFilePath);

        log.debug("temp sensor read from file fileContent: " + fileContent);

        String temperature = StringUtils.firstStingBetween(
                fileContent,
                "t=",
                "\n");

        log.debug("Read temperature: " + temperature);

        float rawValue = Float.valueOf(temperature);

        value = rawValue / 1000;

        log.debug("Parsed value " + value + " from " + getSensorValueFilePath());
    }
}

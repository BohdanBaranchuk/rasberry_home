package com.homedev.smart_home.smart89.v1.initializers;

import com.homedev.smart_home.smart89.v1.config.FloorConfig;
import com.homedev.smart_home.smart89.v1.config.FloorsConfig;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystemMode;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.HeatFloorAutomaticSystem;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.DiscreteOutput;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl.DiscreteOutputImpl;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl.sensor.TemperatureSensor;
import com.homedev.smart_home.smart89.v1.domain.models.home.Flat;
import com.homedev.smart_home.smart89.v1.domain.models.home.rooms.Room;
import com.homedev.smart_home.smart89.v1.domain.models.scheduler.ControlSystemScheduler;
import com.pi4j.io.gpio.PinState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeatFloorSystemsInitializer {

    private static final Logger log = LoggerFactory.getLogger(
            HeatFloorSystemsInitializer.class);

    private Flat flat;

    private FloorsConfig floorsConfig;

    private ControlSystemScheduler scheduler;

    @Autowired
    public HeatFloorSystemsInitializer(
            Flat flat,
            FloorsConfig floorsConfig,
            ControlSystemScheduler scheduler) {

        this.flat = flat;
        this.floorsConfig = floorsConfig;
        this.scheduler = scheduler;
    }

    void init() {

        log.info("Initialize hardware devices");

        for (FloorConfig floorConfig : floorsConfig.getFloorsConfig()) {

            String sensorValueFilePath = floorConfig.getSensorValueFilePath();

            TemperatureSensor temperatureSensor = new TemperatureSensor(
                    sensorValueFilePath);

            scheduler.addToEverySecondTasks(temperatureSensor);

            String roomName = floorConfig.getRoomName();

            int valvePinNumber = floorConfig.getValvePinNumber();

            DiscreteOutput heatFloorValve = new DiscreteOutputImpl(
                    valvePinNumber,
                    roomName + " heat floor valve",
                    PinState.LOW);

            HeatFloorAutomaticSystem heatFloorSystem = new HeatFloorAutomaticSystem(
                    roomName + " heat floor system",
                    temperatureSensor,
                    heatFloorValve);

            heatFloorSystem.setMode(AutomaticSystemMode.AUTO);
            heatFloorSystem.setDesiredTemperature(25);

            scheduler.addToHalfMinuteTasks(heatFloorSystem);

            Room room = new Room("Corridor");
            room.addControlSystem(heatFloorSystem);

            flat.addRoom(room);
        }
    }
}

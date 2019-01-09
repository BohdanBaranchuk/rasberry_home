package com.homedev.smart_home.smart89.v1.initializers.impl;

import com.homedev.smart_home.smart89.v1.config.floor.FloorConfig;
import com.homedev.smart_home.smart89.v1.config.floor.FloorsConfig;
import com.homedev.smart_home.smart89.v1.controllers.utils.RoomUtils;
import com.homedev.smart_home.smart89.v1.database.domain.HeatFloorDatabaseModel;
import com.homedev.smart_home.smart89.v1.database.repository.HeatFloorDatabaseModelRepository;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystemMode;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.HeatFloorAutomaticSystem;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.DiscreteOutput;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl.DiscreteOutputImpl;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl.sensor.TemperatureSensor;
import com.homedev.smart_home.smart89.v1.domain.models.home.Flat;
import com.homedev.smart_home.smart89.v1.domain.models.home.rooms.Room;
import com.homedev.smart_home.smart89.v1.domain.models.scheduler.ControlSystemScheduler;
import com.homedev.smart_home.smart89.v1.initializers.Initializer;
import com.pi4j.io.gpio.PinState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HeatFloorSystemsInitializer implements Initializer {

    private static final Logger log = LoggerFactory.getLogger(
            HeatFloorSystemsInitializer.class);

    private Flat flat;

    private FloorsConfig floorsConfig;

    private ControlSystemScheduler scheduler;

    private HeatFloorDatabaseModelRepository heatFloorRepository;

    @Autowired
    public HeatFloorSystemsInitializer(
            Flat flat,
            FloorsConfig floorsConfig,
            ControlSystemScheduler scheduler,
            HeatFloorDatabaseModelRepository heatFloorRepository) {

        this.flat = flat;
        this.floorsConfig = floorsConfig;
        this.scheduler = scheduler;
        this.heatFloorRepository = heatFloorRepository;
    }

    public void init() throws Exception {

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

            HeatFloorDatabaseModel heatFloorDatabaseModel = heatFloorRepository.findHeatFloorByRoomName(roomName);

            AutomaticSystemMode startupMode;

            float startupDesiredTemperature;

            if (heatFloorDatabaseModel != null) {

                log.info("+++ read from db: " + heatFloorDatabaseModel);

                String modeName = heatFloorDatabaseModel.getModeName();
                startupMode = AutomaticSystemMode.valueOf(modeName);

                startupDesiredTemperature = heatFloorDatabaseModel.getDesiredTemperature();

            } else {

                String startupModeName = floorConfig.getDefaultStartupMode();
                startupMode = AutomaticSystemMode.valueOf(startupModeName); // TODO: read from config

                startupDesiredTemperature = floorConfig.getDefaultStartupTemperature();

                HeatFloorDatabaseModel defaultHeatFloorDatabaseModel = new HeatFloorDatabaseModel();

                defaultHeatFloorDatabaseModel.setRoomName(roomName);
                defaultHeatFloorDatabaseModel.setDesiredTemperature(startupDesiredTemperature);
                defaultHeatFloorDatabaseModel.setModeName(startupModeName);

                heatFloorRepository.save(defaultHeatFloorDatabaseModel);

                log.info("+++ saved to db: " + defaultHeatFloorDatabaseModel);
            }

            heatFloorSystem.setMode(startupMode);
            heatFloorSystem.setDesiredTemperature(startupDesiredTemperature);

            scheduler.addToHalfMinuteTasks(heatFloorSystem);

            Room room = RoomUtils.findRoomByName(
                    flat,
                    roomName);

            if (room == null) {
                room = new Room(roomName);
                flat.addRoom(room);
            }

            room.addControlSystem(heatFloorSystem);
        }
    }

    public String getName() {
        return "Heat floor initializer";
    }
}

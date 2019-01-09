package com.homedev.smart_home.smart89.v1.initializers.impl;

import com.homedev.smart_home.smart89.v1.config.air.AirConfig;
import com.homedev.smart_home.smart89.v1.controllers.utils.RoomUtils;
import com.homedev.smart_home.smart89.v1.database.domain.AirDatabaseModel;
import com.homedev.smart_home.smart89.v1.database.repository.AirDatabaseModelRepository;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AirAutomaticSystem;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystemMode;
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
public class AirSystemsInitializer implements Initializer {

    private static final Logger log = LoggerFactory.getLogger(
            AirSystemsInitializer.class);

    private Flat flat;

    private AirConfig airConfig;

    private ControlSystemScheduler scheduler;

    private AirDatabaseModelRepository airRepository;

    @Autowired
    public AirSystemsInitializer(
            Flat flat,
            AirConfig airConfig,
            ControlSystemScheduler scheduler,
            AirDatabaseModelRepository airRepository) {

        this.flat = flat;
        this.airConfig = airConfig;
        this.scheduler = scheduler;
        this.airRepository = airRepository;
    }

    public void init() throws Exception {

        log.info("Initialize air hardware devices");

        String sensorValueFilePath = airConfig.getSensorValueFilePath();

        TemperatureSensor temperatureSensor = new TemperatureSensor(
                sensorValueFilePath);

        scheduler.addToEverySecondTasks(temperatureSensor);

        String roomName = airConfig.getRoomName();

        int boilerPinNumber = airConfig.getBoilerPinNumber();

        DiscreteOutput heatFloorBoiler = new DiscreteOutputImpl(
                boilerPinNumber,
                roomName + " air boiler",
                PinState.LOW);

        AirAutomaticSystem airSystem = new AirAutomaticSystem(
                roomName + " air system",
                temperatureSensor,
                heatFloorBoiler);

        AirDatabaseModel airDatabaseModel = airRepository.findAirByRoomName(roomName);

        log.info("airDatabaseModel from repository: " + airDatabaseModel);

        AutomaticSystemMode startupMode;

        float startupDesiredTemperature;

        if (airDatabaseModel != null) {

            log.info("Read air model from DB: " + airDatabaseModel);

            String modeName = airDatabaseModel.getModeName();
            startupMode = AutomaticSystemMode.valueOf(modeName);

            startupDesiredTemperature = airDatabaseModel.getDesiredTemperature();

        } else {

            String startupModeName = airConfig.getDefaultStartupMode();
            startupMode = AutomaticSystemMode.valueOf(startupModeName);

            startupDesiredTemperature = airConfig.getDefaultStartupTemperature();

            AirDatabaseModel defaultAirDatabaseModel = new AirDatabaseModel();

            defaultAirDatabaseModel.setRoomName(roomName);
            defaultAirDatabaseModel.setDesiredTemperature(startupDesiredTemperature);
            defaultAirDatabaseModel.setModeName(startupModeName);

            airRepository.save(defaultAirDatabaseModel);

            log.info("Saved to DB defaultAirDatabaseModel: " + defaultAirDatabaseModel);
        }

        airSystem.setMode(startupMode);
        airSystem.setDesiredTemperature(startupDesiredTemperature);

        scheduler.addToHalfMinuteTasks(airSystem);

        Room room = RoomUtils.findRoomByName(
                flat,
                roomName);
        // TODO: move rooms initialize to other class
        // TODO: create diff config class for rooms init from yml

        log.info("Read room from flat " + room);

        if (room == null) {

            log.info("Create new room with name: " + roomName);

            room = new Room(roomName);
            flat.addRoom(room);
        }

        log.info("Add air control system to room");

        room.addControlSystem(airSystem);

        log.info("Total automatic systems count in room: " + room.getAutomaticSystems().size());
    }

    public String getName() {
        return "Air systems initializer";
    }
}

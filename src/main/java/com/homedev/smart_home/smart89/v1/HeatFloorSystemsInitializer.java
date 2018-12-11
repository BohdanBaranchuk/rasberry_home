package com.homedev.smart_home.smart89.v1;

import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.HeatFloorAutomaticSystem;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.DiscreteOutput;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl.DiscreteOutputImpl;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.impl.sensor.TemperatureSensor;
import com.homedev.smart_home.smart89.v1.domain.models.home.Flat;
import com.homedev.smart_home.smart89.v1.domain.models.home.FlatProvider;
import com.homedev.smart_home.smart89.v1.domain.models.home.rooms.Room;
import com.homedev.smart_home.smart89.v1.domain.models.scheduler.ControlSystemScheduler;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

class HeatFloorSystemsInitializer {

    private static final Logger log = LoggerFactory.getLogger(
            HeatFloorSystemsInitializer.class);

    static void init(ApplicationContext applicationContext) {

        log.info("Initialize hardware devices");

        Flat flat = FlatProvider.getFlat();

        ControlSystemScheduler scheduler = applicationContext.getBean(
                ControlSystemScheduler.class);

        // Bathroom
        TemperatureSensor bathroomTempSensor = new TemperatureSensor(
                "/sys/bus/w1/devices/28-051685287cff/w1_slave",
                3,
                "Bath sensor");
        scheduler.addToEverySecondTasks(bathroomTempSensor);

        DiscreteOutput bathroomHeatFloorValve = new DiscreteOutputImpl(
                RaspiPin.GPIO_08,
                "Bath heat floor valve",
                PinState.LOW);

        HeatFloorAutomaticSystem bathroomHeatFloorSystem = new HeatFloorAutomaticSystem(
                "Bathroom heat floor system",
                bathroomTempSensor,
                bathroomHeatFloorValve,
                25.0F);
        scheduler.addToHalfMinuteTasks(bathroomHeatFloorSystem);

        // Kitchen
        TemperatureSensor kitchenTempSensor = new TemperatureSensor(
                "/sys/bus/w1/devices/28-031683f660ff/w1_slave",
                1,
                "Kitchen sensor");
        scheduler.addToEverySecondTasks(kitchenTempSensor);

        DiscreteOutput kitchenHeatFloorValve = new DiscreteOutputImpl(
                RaspiPin.GPIO_04,
                "Kitchen heat floor valve",
                PinState.LOW);

        HeatFloorAutomaticSystem kitchenHeatFloor = new HeatFloorAutomaticSystem(
                "Kitchen heat floor system",
                kitchenTempSensor,
                kitchenHeatFloorValve,
                25);
        scheduler.addToHalfMinuteTasks(kitchenHeatFloor);

        // Corridor
        TemperatureSensor corridorTempSensor = new TemperatureSensor(
                "/sys/bus/w1/devices/28-0516853274ff/w1_slave",
                2,
                "Corridor sensor");
        scheduler.addToEverySecondTasks(corridorTempSensor);

        DiscreteOutput corridorHeatFloorValve = new DiscreteOutputImpl(
                RaspiPin.GPIO_05,
                "Corridor heat floor valve",
                PinState.LOW);

        HeatFloorAutomaticSystem corridorHeatFloor = new HeatFloorAutomaticSystem(
                "Corridor heat floor",
                corridorTempSensor,
                corridorHeatFloorValve,
                25);
        scheduler.addToHalfMinuteTasks(corridorHeatFloor);

        Room corridor = new Room("Corridor");
        corridor.addControlSystem(corridorHeatFloor);

        Room kitchen = new Room("Kitchen");
        kitchen.addControlSystem(kitchenHeatFloor);

        Room bathroom = new Room("Bathroom");
        bathroom.addControlSystem(bathroomHeatFloorSystem);

        flat.addRoom(kitchen);
        flat.addRoom(corridor);
        flat.addRoom(bathroom);
    }
}

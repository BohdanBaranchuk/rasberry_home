package com.homedev.smart_home.smart89.v1.controllers;

import com.homedev.smart_home.smart89.v1.controllers.front_model.AirModel;
import com.homedev.smart_home.smart89.v1.controllers.utils.RoomUtils;
import com.homedev.smart_home.smart89.v1.database.domain.AirDatabaseModel;
import com.homedev.smart_home.smart89.v1.database.repository.AirDatabaseModelRepository;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AirAutomaticSystem;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystem;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystemMode;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.sensor.Sensor;
import com.homedev.smart_home.smart89.v1.domain.models.home.Flat;
import com.homedev.smart_home.smart89.v1.domain.models.home.rooms.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/air")
public class AirPageController {

    private static final Logger log = LoggerFactory.getLogger(
            AirPageController.class);

    private static final String METRIC_NAME_GET_AIR_PAGE = "get-air-page";
    private static final String METRIC_NAME_AIR_TEMP_UP = "air-temp-up";
    private static final String METRIC_NAME_AIR_TEMP_DOWN = "air-temp-down";
    private static final String METRIC_NAME_AIR_SET_MODE = "air-set-mode";

    private static final String METRIC_NAME_AIR_PAGE_LAST_ACCESS_TIME = "air-page-las-access-time";

    private Flat flat;

    private AirDatabaseModelRepository airRepository;

    private CounterService counterService;

    private GaugeService gaugeService;

    @Autowired
    public AirPageController(
            Flat flat,
            AirDatabaseModelRepository airRepository,
            CounterService counterService,
            GaugeService gaugeService) {

        this.flat = flat;
        this.airRepository = airRepository;
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    @GetMapping
    public String getMainPage(Model model) {

        counterService.increment(METRIC_NAME_GET_AIR_PAGE);

        gaugeService.submit(METRIC_NAME_AIR_PAGE_LAST_ACCESS_TIME, System.currentTimeMillis());

        List<AirModel> frontModels = new ArrayList<>();

        for (Room room : flat) {

            log.info("Get room from flat with name: " + room.getName());

            List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

            for (AutomaticSystem automaticSystem : automaticSystems) {

                log.info("Get room automatic system with name: " + automaticSystem.getType().name());

                if (automaticSystem instanceof AirAutomaticSystem) {

                    log.info("Find air system: " + automaticSystem.getName());

                    AirAutomaticSystem airSystem = (AirAutomaticSystem) automaticSystem;

                    String roomName = room.getName();

                    Sensor sensor = airSystem.getControlledSensor();
                    float currentTemperature = sensor.getValue();

                    int desiredTemperature = (int) airSystem.getDesiredTemperature();

                    String modeName = airSystem.getMode().name();

                    AirModel airModel = new AirModel(
                            roomName,
                            currentTemperature,
                            desiredTemperature,
                            modeName);

                    log.info("created air model: " + airModel);

                    frontModels.add(airModel);
                }
            }
        }

        model.addAttribute("airSystems", frontModels);

        return "air";
    }

    @GetMapping("/hellpair")
    public String sendModel(Model model) {

        AirModel airModel = new AirModel(
                "Bedroom",
                27F,
                32,
                AutomaticSystemMode.AUTO.name());

        model.addAttribute("airSystem", airModel);

        return "air";
    }

    @RequestMapping(
            value = "/setTempUp",
            method = RequestMethod.POST)
    public String setTempUp(
            @RequestParam("roomName") String roomName,
            Model model) {

        counterService.increment(METRIC_NAME_AIR_TEMP_UP);

        Room room = RoomUtils.findRoomByName(
                flat,
                roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof AirAutomaticSystem) {

                AirAutomaticSystem airAutomaticSystem = (AirAutomaticSystem) automaticSystem;

                float currentDesiredTemperatute = airAutomaticSystem.getDesiredTemperature();

                float increasedDesiredTemperature = currentDesiredTemperatute + 1;

                AirDatabaseModel airDatabaseModel = airRepository.findAirByRoomName(roomName);

                if (airDatabaseModel != null) {

                    airDatabaseModel.setDesiredTemperature(increasedDesiredTemperature);

                    airRepository.saveAndFlush(airDatabaseModel);
                }

                airAutomaticSystem.setDesiredTemperature(increasedDesiredTemperature);

                log.info("Temp up action for air system in room: " + room);

                break;
            }
        }

        return "air";
    }

    @RequestMapping(
            value = "/setTempDown",
            method = RequestMethod.POST)
    public String setTempDown(
            @RequestParam("roomName") String roomName,
            Model model) {

        counterService.increment(METRIC_NAME_AIR_TEMP_DOWN);

        Room room = RoomUtils.findRoomByName(
                flat,
                roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof AirAutomaticSystem) {

                AirAutomaticSystem airAutomaticSystem = (AirAutomaticSystem) automaticSystem;

                float currentDesiredTemperatute = airAutomaticSystem.getDesiredTemperature();

                float decreasedDesiredTemperature = currentDesiredTemperatute - 1;

                AirDatabaseModel airDatabaseModel = airRepository.findAirByRoomName(roomName);

                if (airDatabaseModel != null) {

                    airDatabaseModel.setDesiredTemperature(decreasedDesiredTemperature);

                    airRepository.saveAndFlush(airDatabaseModel);
                }

                airAutomaticSystem.setDesiredTemperature(decreasedDesiredTemperature);

                log.info("Temp down for air system in room: " + room);

                break;
            }
        }

        return "air";
    }

    @RequestMapping(
            value = "/setRoomMode",
            method = RequestMethod.POST)
    public String setAutomaticMode(
            @RequestParam("roomName") String roomName,
            @RequestParam("modeName") String modeName,
            Model model) {

        counterService.increment(METRIC_NAME_AIR_SET_MODE);

        AutomaticSystemMode automaticSystemMode = AutomaticSystemMode.valueOf(modeName);

        Room room = RoomUtils.findRoomByName(
                flat,
                roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof AirAutomaticSystem) {

                AirAutomaticSystem airAutomaticSystem = (AirAutomaticSystem) automaticSystem;

                AirDatabaseModel airDatabaseModel = airRepository.findAirByRoomName(roomName);

                if (airDatabaseModel != null) {

                    airDatabaseModel.setModeName(automaticSystemMode.name());

                    airRepository.saveAndFlush(airDatabaseModel);
                }

                airAutomaticSystem.setMode(automaticSystemMode);

                log.info("Set mode " + automaticSystemMode + " for airAutomaticSystem: " + airAutomaticSystem);

                break;
            }
        }

        return "air";
    }
}

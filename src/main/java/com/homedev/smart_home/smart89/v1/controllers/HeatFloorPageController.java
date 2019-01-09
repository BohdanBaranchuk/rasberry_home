package com.homedev.smart_home.smart89.v1.controllers;

import com.homedev.smart_home.smart89.v1.controllers.front_model.HeatFloorModel;
import com.homedev.smart_home.smart89.v1.controllers.utils.RoomUtils;
import com.homedev.smart_home.smart89.v1.database.domain.HeatFloorDatabaseModel;
import com.homedev.smart_home.smart89.v1.database.repository.HeatFloorDatabaseModelRepository;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystem;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystemMode;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystemType;
import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.HeatFloorAutomaticSystem;
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
@RequestMapping(value = "/floor")
public class HeatFloorPageController {

    private static final Logger log = LoggerFactory.getLogger(
            HeatFloorPageController.class);

    private static final String METRIC_NAME_GET_FLOOR_PAGE = "get-floor-page";
    private static final String METRIC_NAME_FLOOR_TEMP_UP = "floor-temp-up";
    private static final String METRIC_NAME_FLOOR_TEMP_DOWN = "floor-temp-down";
    private static final String METRIC_NAME_FLOOR_SET_MODE = "floor-set-mode";

    private static final String METRIC_NAME_FLOOR_PAGE_LAST_ACCESS_TIME = "floor-page-las-access-time";

    private Flat flat;

    private HeatFloorDatabaseModelRepository heatFloorRepository;

    private CounterService counterService;

    private GaugeService gaugeService;

    @Autowired
    public HeatFloorPageController(
            Flat flat,
            HeatFloorDatabaseModelRepository heatFloorRepository,
            CounterService counterService,
            GaugeService gaugeService) {

        this.flat = flat;
        this.heatFloorRepository = heatFloorRepository;
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    @GetMapping
    public String floorFormAction(Model model) {

        counterService.increment(METRIC_NAME_GET_FLOOR_PAGE);

        gaugeService.submit(METRIC_NAME_FLOOR_PAGE_LAST_ACCESS_TIME, System.currentTimeMillis());

        List<HeatFloorModel> frontModels = new ArrayList<>();

        for (Room room : flat) {

            List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

            for (AutomaticSystem automaticSystem : automaticSystems) {

                if (automaticSystem.getType() == AutomaticSystemType.HEATING_FLOOR) {

                    HeatFloorAutomaticSystem floorSystem = (HeatFloorAutomaticSystem) automaticSystem;

                    String roomName = room.getName();

                    Sensor sensor = floorSystem.getControlledSensor();
                    float currentTemperature = sensor.getValue();

                    int desiredTemperature = (int) floorSystem.getDesiredTemperature();

                    String modeName = floorSystem.getMode().name();

                    HeatFloorModel heatFloorModel = new HeatFloorModel(
                            roomName,
                            currentTemperature,
                            desiredTemperature,
                            modeName);

                    log.info("created heatFloorModel: " + heatFloorModel);

                    frontModels.add(heatFloorModel);
                }
            }
        }

        model.addAttribute("heatSystems", frontModels);

        return "floor";
    }

    @GetMapping("/hellp")
    public String sendModel(Model model) {

        List<HeatFloorModel> frontModels = new ArrayList<>();

        for (int i = 0; i < 3; i++) {

            HeatFloorModel heatFloorModel = new HeatFloorModel(
                    "Kitchen" + i,
                    27F,
                    32,
                    AutomaticSystemMode.values()[i].name());

            frontModels.add(heatFloorModel);
        }

        model.addAttribute("heatSystems", frontModels);

        return "floor";
    }

    @RequestMapping(
            value = "/setTempUp",
            method = RequestMethod.POST)
    public String setTempUp(
            @RequestParam("roomName") String roomName,
            Model model) {

        counterService.increment(METRIC_NAME_FLOOR_TEMP_UP);

        Room room = RoomUtils.findRoomByName(
                flat,
                roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof HeatFloorAutomaticSystem) {

                HeatFloorAutomaticSystem heatFloorAutomaticSystem = (HeatFloorAutomaticSystem) automaticSystem;

                float currentDesiredTemperatute = heatFloorAutomaticSystem.getDesiredTemperature();

                float increasedDesiredTemperature = currentDesiredTemperatute + 1;

                HeatFloorDatabaseModel heatFloorDatabaseModel = heatFloorRepository.findHeatFloorByRoomName(roomName);

                if (heatFloorDatabaseModel != null) {

                    heatFloorDatabaseModel.setDesiredTemperature(increasedDesiredTemperature);

                    heatFloorRepository.saveAndFlush(heatFloorDatabaseModel);
                }

                heatFloorAutomaticSystem.setDesiredTemperature(increasedDesiredTemperature);

                log.info("setTempUp action for room: " + room);

                break;
            }
        }

        return "floor";
    }

    @RequestMapping(
            value = "/setTempDown",
            method = RequestMethod.POST)
    public String setTempDown(
            @RequestParam("roomName") String roomName,
            Model model) {

        counterService.increment(METRIC_NAME_FLOOR_TEMP_DOWN);

        Room room = RoomUtils.findRoomByName(
                flat,
                roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof HeatFloorAutomaticSystem) {

                HeatFloorAutomaticSystem heatFloorAutomaticSystem = (HeatFloorAutomaticSystem) automaticSystem;

                float currentDesiredTemperatute = heatFloorAutomaticSystem.getDesiredTemperature();

                float decreasedDesiredTemperature = currentDesiredTemperatute - 1;

                HeatFloorDatabaseModel heatFloorDatabaseModel = heatFloorRepository.findHeatFloorByRoomName(roomName);

                if (heatFloorDatabaseModel != null) {

                    heatFloorDatabaseModel.setDesiredTemperature(decreasedDesiredTemperature);

                    heatFloorRepository.saveAndFlush(heatFloorDatabaseModel);
                }

                heatFloorAutomaticSystem.setDesiredTemperature(decreasedDesiredTemperature);

                log.info("setTempDown action for room: " + room);

                break;
            }
        }

        return "floor";
    }

    @RequestMapping(
            value = "/setRoomMode",
            method = RequestMethod.POST)
    public String setAutomaticMode(
            @RequestParam("roomName") String roomName,
            @RequestParam("modeName") String modeName,
            Model model) {

        counterService.increment(METRIC_NAME_FLOOR_SET_MODE);

        AutomaticSystemMode automaticSystemMode = AutomaticSystemMode.valueOf(modeName);

        Room room = RoomUtils.findRoomByName(
                flat,
                roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof HeatFloorAutomaticSystem) {

                HeatFloorAutomaticSystem heatFloorAutomaticSystem = (HeatFloorAutomaticSystem) automaticSystem;

                HeatFloorDatabaseModel heatFloorDatabaseModel = heatFloorRepository.findHeatFloorByRoomName(roomName);

                if (heatFloorDatabaseModel != null) {

                    heatFloorDatabaseModel.setModeName(automaticSystemMode.name());

                    heatFloorRepository.saveAndFlush(heatFloorDatabaseModel);
                }

                heatFloorAutomaticSystem.setMode(automaticSystemMode);

                log.info("Set mode " + automaticSystemMode + " for heatFloorAutomaticSystem: " + heatFloorAutomaticSystem);

                break;
            }
        }

        return "floor";
    }
}

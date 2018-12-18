package com.homedev.smart_home.smart89.v1.controllers;

import com.homedev.smart_home.smart89.v1.controllers.front_model.HeatFloorModel;
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
public class HeatFloorPageController {

    private static final Logger log = LoggerFactory.getLogger(
            HeatFloorPageController.class);

    @Autowired
    private Flat flat;

    @GetMapping("/floor")
    public String floorFormAction(Model model) {

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

        return "starter";
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

        return "starter";
    }

    @RequestMapping(
            value = "/setTempUp",
            method = RequestMethod.POST)
    public String setTempUp(
            @RequestParam("roomName") String roomName,
            Model model) {

        Room room = getRoomByName(roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof HeatFloorAutomaticSystem) {

                HeatFloorAutomaticSystem heatFloorAutomaticSystem = (HeatFloorAutomaticSystem) automaticSystem;

                float currentDesiredTemperatute = heatFloorAutomaticSystem.getDesiredTemperature();

                float increasedDesiredTemperature = currentDesiredTemperatute + 1;

                heatFloorAutomaticSystem.setDesiredTemperature(increasedDesiredTemperature);

                log.info("setTempUp action for room: " + room);

                break;
            }
        }

        return "starter";
    }

    @RequestMapping(
            value = "/setTempDown",
            method = RequestMethod.POST)
    public String setTempDown(
            @RequestParam("roomName") String roomName,
            Model model) {

        Room room = getRoomByName(roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof HeatFloorAutomaticSystem) {

                HeatFloorAutomaticSystem heatFloorAutomaticSystem = (HeatFloorAutomaticSystem) automaticSystem;

                float currentDesiredTemperatute = heatFloorAutomaticSystem.getDesiredTemperature();

                float decreasedDesiredTemperature = currentDesiredTemperatute - 1;

                heatFloorAutomaticSystem.setDesiredTemperature(decreasedDesiredTemperature);

                log.info("setTempDown action for room: " + room);

                break;
            }
        }

        return "starter";
    }

    @RequestMapping(
            value = "/setRoomMode",
            method = RequestMethod.POST)
    public String setAutomaticMode(
            @RequestParam("roomName") String roomName,
            @RequestParam("modeName") String modeName,
            Model model) {

        AutomaticSystemMode automaticSystemMode = AutomaticSystemMode.valueOf(modeName);

        Room room = getRoomByName(roomName);

        List<AutomaticSystem> automaticSystems = room.getAutomaticSystems();

        for (AutomaticSystem automaticSystem : automaticSystems) {

            if (automaticSystem instanceof HeatFloorAutomaticSystem) {

                HeatFloorAutomaticSystem heatFloorAutomaticSystem = (HeatFloorAutomaticSystem) automaticSystem;

                heatFloorAutomaticSystem.setMode(automaticSystemMode);

                log.info("Set mode " + automaticSystemMode + " for heatFloorAutomaticSystem: " + heatFloorAutomaticSystem);

                break;
            }
        }

        return "starter";
    }

    private Room getRoomByName(String roomName) {

        for (Room room : flat) {
            String flatRoomName = room.getName();

            if (flatRoomName.equals(roomName)) {
                return room;
            }
        }

        log.error("Not found room by name: " + roomName);

        throw new RuntimeException("Not found room by name: " + roomName);
    }

}

package com.homedev.smart_home.smart89.v1.domain.models.home.rooms;

import com.homedev.smart_home.smart89.v1.domain.models.automatic_systems.AutomaticSystem;
import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;

    private List<AutomaticSystem> automaticSystems = new ArrayList<>();

    public Room(
            String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addControlSystem(AutomaticSystem automaticSystem) {
        automaticSystems.add(automaticSystem);
    }

    public List<AutomaticSystem> getAutomaticSystems() {
        return automaticSystems;
    }
}

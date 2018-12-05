package com.homedev.smart_home.smart89.v1.domain.models.automatic_systems;

public abstract class AutomaticSystem {

    private String name;

    private AutomaticSystemType type;

    AutomaticSystemMode mode = AutomaticSystemMode.OFF;

    public AutomaticSystem(
            String name,
            AutomaticSystemType type) {

        this.name = name;
        this.type = type;
    }

    public abstract void doAutomaticScheduledAction();

    public String getName() {
        return name;
    }

    public AutomaticSystemType getType() {
        return type;
    }

    public AutomaticSystemMode getMode() {
        return mode;
    }

    public void setMode(AutomaticSystemMode mode) {
        this.mode = mode;
    }
}

package com.homedev.smart_home.smart89.v1.domain.models.light;

import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.DiscreteOutput;

public class LightGroup {

    private int id;

    private String name;

    private DiscreteOutput output;

    public LightGroup(
            int id,
            String name,
            DiscreteOutput output) {

        this.id = id;
        this.name = name;
        this.output = output;
    }

    public void on() {
        output.close();
    }

    public void off() {
        output.open();
    }

    public void toggle() {
        output.toggle();
    }

    public void pulseON(long pulseTime) {
        output.pulseClose(pulseTime);
    }
}

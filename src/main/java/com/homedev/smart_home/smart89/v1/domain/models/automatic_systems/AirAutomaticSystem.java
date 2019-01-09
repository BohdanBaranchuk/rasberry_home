package com.homedev.smart_home.smart89.v1.domain.models.automatic_systems;


import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.DiscreteOutput;
import com.homedev.smart_home.smart89.v1.domain.models.hardware.rasberry.api.sensor.Sensor;
import com.homedev.smart_home.smart89.v1.domain.models.scheduler.ScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirAutomaticSystem extends AutomaticSystem implements ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(
            AirAutomaticSystem.class);

    private Sensor controlledSensor;

    private DiscreteOutput actuatingOutput;

    private float desiredTemperature;

    public AirAutomaticSystem(
            String name,
            Sensor controlledSensor,
            DiscreteOutput actuatingOutput) {

        super(name, AutomaticSystemType.AIR_CONTROL);

        this.controlledSensor = controlledSensor;
        this.actuatingOutput = actuatingOutput;
    }

    public void performTask() {
        doAutomaticScheduledAction();
    }

    public void doAutomaticScheduledAction() {

        log.info("Do control action in air system with name: " + getName());

        switch (mode) {

            case AUTO:
                doControlActionInAutoMode();
                break;

            case ON:
                doControlActionInOnMode();
                break;

            case OFF:
                doControlActionInOffMode();
                break;

            default:
                log.error("Not supported mode: " + mode);
                throw new RuntimeException("Not supported mode: " + mode);
        }
    }

    private void doControlActionInAutoMode() {

        float currentTemperature = controlledSensor.getValue();

        log.info("currentTemperature: " + currentTemperature);
        log.info("desiredTemperature: " + desiredTemperature);

        if (currentTemperature >= desiredTemperature) {
            doControlActionInOffMode();
        } else {
            doControlActionInOnMode();
        }
    }

    private void doControlActionInOnMode() {
        log.info("send command output close from system: " + getName());
        actuatingOutput.close();
    }

    private void doControlActionInOffMode() {
        log.info("send command output open from system " + getName());
        actuatingOutput.open();
    }

    public Sensor getControlledSensor() {
        return controlledSensor;
    }

    public float getDesiredTemperature() {
        return desiredTemperature;
    }

    public void setDesiredTemperature(float desiredTemperature) {
        this.desiredTemperature = desiredTemperature;
    }
}

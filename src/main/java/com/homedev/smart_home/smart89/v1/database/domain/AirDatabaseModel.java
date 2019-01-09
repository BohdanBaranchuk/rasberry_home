package com.homedev.smart_home.smart89.v1.database.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AirDatabaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String roomName;

    private String modeName;

    private float desiredTemperature;

    public AirDatabaseModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public float getDesiredTemperature() {
        return desiredTemperature;
    }

    public void setDesiredTemperature(float desiredTemperature) {
        this.desiredTemperature = desiredTemperature;
    }
}

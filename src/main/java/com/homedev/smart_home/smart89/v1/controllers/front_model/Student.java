package com.homedev.smart_home.smart89.v1.controllers.front_model;

import java.io.Serializable;

public class Student implements Serializable {

    private String id;
    private String name;

    private int value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

package com.homedev.smart_home.smart89.v1.domain.models.home;

public class FlatProvider {

    private static final Flat flat = new Flat("Our home");

    private FlatProvider() {
    }

    public static Flat getFlat() {
        return flat;
    }
}

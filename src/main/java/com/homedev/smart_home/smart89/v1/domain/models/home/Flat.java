package com.homedev.smart_home.smart89.v1.domain.models.home;

import com.homedev.smart_home.smart89.v1.domain.models.home.rooms.Room;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Flat implements Iterable<Room> {

    private String name;

    private List<Room> rooms = new ArrayList<>();

    public Flat(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Iterator<Room> iterator() {
        return rooms.iterator();
    }
}

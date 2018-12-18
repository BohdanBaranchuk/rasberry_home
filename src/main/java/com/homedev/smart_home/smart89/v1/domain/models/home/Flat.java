package com.homedev.smart_home.smart89.v1.domain.models.home;

import com.homedev.smart_home.smart89.v1.domain.models.home.rooms.Room;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class Flat implements Iterable<Room> {

    private List<Room> rooms = new ArrayList<>();

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

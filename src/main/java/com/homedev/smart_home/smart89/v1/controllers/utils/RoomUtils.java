package com.homedev.smart_home.smart89.v1.controllers.utils;

import com.homedev.smart_home.smart89.v1.domain.models.home.Flat;
import com.homedev.smart_home.smart89.v1.domain.models.home.rooms.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomUtils {

    private static final Logger log = LoggerFactory.getLogger(
            RoomUtils.class);

    public static Room findRoomByName(
            Flat flat,
            String roomName) {

        for (Room room : flat) {

            String flatRoomName = room.getName();

            if (flatRoomName.equals(roomName)) {
                return room;
            }
        }

        log.error("Not found room by name: " + roomName);

        return null; // Change to Optional from java 8
    }
}

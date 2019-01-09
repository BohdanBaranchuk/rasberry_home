package com.homedev.smart_home.smart89.v1.database.repository;


import com.homedev.smart_home.smart89.v1.database.domain.AirDatabaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirDatabaseModelRepository extends JpaRepository<AirDatabaseModel, Long> {

    AirDatabaseModel findAirByRoomName(String roomName);
}

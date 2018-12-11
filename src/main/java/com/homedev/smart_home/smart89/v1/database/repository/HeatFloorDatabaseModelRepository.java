package com.homedev.smart_home.smart89.v1.database.repository;


import com.homedev.smart_home.smart89.v1.database.domain.HeatFloorDatabaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeatFloorDatabaseModelRepository extends JpaRepository<HeatFloorDatabaseModel, Long> {

    HeatFloorDatabaseModel findHeatFloorByRoomName(String roomName);
}

package com.homedev.smart_home.smart89.v1.initializers;

import com.homedev.smart_home.smart89.v1.initializers.common.ProfileNames;
import com.homedev.smart_home.smart89.v1.initializers.impl.AirSystemsInitializer;
import com.homedev.smart_home.smart89.v1.initializers.impl.HeatFloorSystemsInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;

@Component
public class HomeSystemsInitializer {

    private static final Logger log = LoggerFactory.getLogger(
            HomeSystemsInitializer.class);

    private Environment environment;

    private List<Initializer> initializers = new LinkedList<>();

    @Autowired
    public HomeSystemsInitializer(
            Environment environment,
            HeatFloorSystemsInitializer floorSystemsInitializer,
            AirSystemsInitializer airSystemsInitializer) {

        this.environment = environment;

        initializers.add(floorSystemsInitializer);
        initializers.add(airSystemsInitializer);
    }

    public void initializeAllHomeSystems() {

        for (String profileName : environment.getActiveProfiles()) {

            if (profileName.equals(ProfileNames.DEVELOPMENT)) {

                log.info("Skip initializing hardware systems duo active profileName: " + profileName);

                return;
            }
        }

        for (Initializer initializer : initializers) {

            try {
                initializer.init();

            } catch (Exception ex) {
                log.error(
                        "Error initializing: " + initializer.getName(), ex);
            }
        }
    }
}

package com.homedev.smart_home.smart89.v1;

import com.homedev.smart_home.smart89.v1.initializers.HomeSystemsInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(
            ApplicationRunner.class);

    public static void main(String[] args) {

        log.info("Start smart home app initialization");

        SpringApplication app = new SpringApplication(ApplicationRunner.class);

        app.setBannerMode(Banner.Mode.OFF);

        ApplicationContext applicationContext = app.run(args);

        HomeSystemsInitializer homeSystemsInitializer = applicationContext.getBean(
                HomeSystemsInitializer.class);

        homeSystemsInitializer.initializeAllHomeSystems();

        log.info("Successfully initialized smart home app");
    }
}

package com.homedev.smart_home.smart89.v1;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApplicationRunner {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ApplicationRunner.class);

        app.setBannerMode(Banner.Mode.OFF);

        ApplicationContext applicationContext = app.run(args);

        //HeatFloorSystemsInitializer.init(applicationContext); // TODO: uncomment this
    }
}

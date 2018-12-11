package com.homedev.smart_home.smart89.v1.domain.models.scheduler;

public class TestableScheduledTask implements ScheduledTask {

    public void performTask() {
        System.out.println("testable task executed");
    }
}

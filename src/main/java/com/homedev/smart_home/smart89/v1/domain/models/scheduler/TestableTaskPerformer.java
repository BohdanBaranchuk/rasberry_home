package com.homedev.smart_home.smart89.v1.domain.models.scheduler;

public class TestableTaskPerformer implements TaskPerformer {

    public void performTask() {
        System.out.println("testable task executed");
    }
}

package com.homedev.smart_home.smart89.v1.domain.models.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ControlSystemScheduler {

    private List<ScheduledTask> everySecondsTasks = new CopyOnWriteArrayList<>();

    private List<ScheduledTask> halfMinuteTasks = new CopyOnWriteArrayList<>();

    private List<ScheduledTask> oneMinuteTasks = new CopyOnWriteArrayList<>();

    public ControlSystemScheduler() {
    }

    public void addToEverySecondTasks(ScheduledTask scheduledTask) {
        everySecondsTasks.add(scheduledTask);
    }

    public void addToOnceMinuteTasks(ScheduledTask scheduledTask) {
        oneMinuteTasks.add(scheduledTask);
    }

    public void addToHalfMinuteTasks(ScheduledTask scheduledTask) {
        halfMinuteTasks.add(scheduledTask);
    }

    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    private void performEverySecondsTasks() {

        for (ScheduledTask performer : everySecondsTasks) {
            performer.performTask();
        }
    }

    @Scheduled(initialDelay = 5000, fixedRate = 30000)
    private void performHalfMinuteTask() {

        for (ScheduledTask performer : halfMinuteTasks) {
            performer.performTask();
        }
    }

    @Scheduled(initialDelay = 5000, fixedRate = 60000)
    private void performEveryMinuteTask() {

        for (ScheduledTask performer : oneMinuteTasks) {
            performer.performTask();
        }
    }
}

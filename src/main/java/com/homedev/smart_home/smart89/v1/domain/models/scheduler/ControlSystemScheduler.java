package com.homedev.smart_home.smart89.v1.domain.models.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ControlSystemScheduler {

    private List<TaskPerformer> tasks = new ArrayList<>();

    public ControlSystemScheduler() {
    }

    public void addTaskPerformer(TaskPerformer taskPerformer) {
        tasks.add(taskPerformer);
    }

    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    private void doTasks() {

        for (TaskPerformer performer : tasks) {
            performer.performTask();
        }
    }
}

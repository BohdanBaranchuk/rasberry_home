package com.homedev.smart_home.smart89.v1.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ApplicationContextMetrics implements PublicMetrics {

    private ApplicationContext context;

    @Autowired
    public ApplicationContextMetrics(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Collection<Metric<?>> metrics() {

        List<Metric<?>> metrics = new ArrayList<>();

        metrics.add(new Metric<>("spring.context.startup-date", context.getStartupDate()));
        metrics.add(new Metric<>("spring.beans.definitions", context.getBeanDefinitionCount()));

        return metrics;
    }
}

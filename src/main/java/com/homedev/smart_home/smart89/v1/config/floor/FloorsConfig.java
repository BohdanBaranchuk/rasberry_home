package com.homedev.smart_home.smart89.v1.config.floor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "smart-home.config")
public class FloorsConfig implements Iterable<FloorConfig> {

    private List<FloorConfig> floorsConfig;

    public List<FloorConfig> getFloorsConfig() {
        return floorsConfig;
    }

    public void setFloorsConfig(List<FloorConfig> floorsConfig) {
        this.floorsConfig = floorsConfig;
    }

    @Override
    public Iterator<FloorConfig> iterator() {
        return floorsConfig.iterator();
    }
}

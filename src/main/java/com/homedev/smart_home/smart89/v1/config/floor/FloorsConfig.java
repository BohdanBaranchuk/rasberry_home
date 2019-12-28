package com.homedev.smart_home.smart89.v1.config.floor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "smart-home.config")
public class FloorsConfig implements Iterable<FloorConfig> {

    private List<FloorConfig> floorsConfig;

    @Override
    public Iterator<FloorConfig> iterator() {
        return floorsConfig.iterator();
    }
}

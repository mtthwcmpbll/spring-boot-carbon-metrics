package com.carbonaware.sci;

import org.springframework.beans.factory.annotation.Value;

public class ConfiguredEmboddiedEmissionsProvider implements EmbodiedEmissionsProvider {

    @Value("${spring.carbon-aware.emboddied-emissions:0.0}")
    private double embodiedEmissions;

    public ConfiguredEmboddiedEmissionsProvider() {}

    @Override
    public double getEmbodiedEmissions() {
        return embodiedEmissions;
    }
}

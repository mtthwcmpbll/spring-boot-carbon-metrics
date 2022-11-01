package com.carbonaware.sci;

public class ConfiguredEmboddiedEmissionsProvider implements EmbodiedEmissionsProvider {

    private double embodiedEmissions;

    public ConfiguredEmboddiedEmissionsProvider(double embodiedEmissions) {
        this.embodiedEmissions = embodiedEmissions;
    }

    @Override
    public double getEmbodiedEmissions() {
        return embodiedEmissions;
    }
}

package com.snowfort.carbon.sci;

public class ConfiguredEmbodiedEmissionsProvider implements EmbodiedEmissionsProvider {

    private double embodiedEmissions;

    public ConfiguredEmbodiedEmissionsProvider(double embodiedEmissions) {
        this.embodiedEmissions = embodiedEmissions;
    }

    @Override
    public double getEmbodiedEmissions() {
        return embodiedEmissions;
    }
}

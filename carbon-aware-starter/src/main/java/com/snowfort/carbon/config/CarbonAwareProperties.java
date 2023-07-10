package com.snowfort.carbon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.carbon-aware")
public class CarbonAwareProperties {

    private String endpoint;

    private String location = "";

    private double embodiedEmissions = 0.0;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getEmbodiedEmissions() {
        return embodiedEmissions;
    }

    public void setEmbodiedEmissions(double embodiedEmissions) {
        this.embodiedEmissions = embodiedEmissions;
    }
}

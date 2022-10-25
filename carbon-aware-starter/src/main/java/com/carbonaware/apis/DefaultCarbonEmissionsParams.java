package com.carbonaware.apis;

public class DefaultCarbonEmissionsParams implements CarbonEmissionsParams {

    private String location;

    public DefaultCarbonEmissionsParams(String location) {
        this.location = location;
    }

    @Override
    public String getLocation() {
        return location;
    }
}

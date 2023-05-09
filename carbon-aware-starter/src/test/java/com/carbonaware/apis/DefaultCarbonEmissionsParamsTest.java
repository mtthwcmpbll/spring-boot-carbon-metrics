package com.carbonaware.apis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCarbonEmissionsParamsTest {

    @Test
    public void testGetLocation() {
        CarbonEmissionsParams params = new DefaultCarbonEmissionsParams("test_location");
        String actualLocation = params.getLocation();
        assertEquals("test_location", actualLocation);
    }
}
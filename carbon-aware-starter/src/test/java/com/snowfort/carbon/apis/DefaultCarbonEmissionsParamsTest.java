package com.snowfort.carbon.apis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultCarbonEmissionsParamsTest {

    @Test
    public void testGetLocation() {
        CarbonEmissionsParams params = new DefaultCarbonEmissionsParams("test_location");
        String actualLocation = params.getLocation();
        assertEquals("test_location", actualLocation);
    }
}
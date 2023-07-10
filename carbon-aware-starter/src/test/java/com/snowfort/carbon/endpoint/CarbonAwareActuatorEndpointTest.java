package com.snowfort.carbon.endpoint;

import com.snowfort.carbon.apis.CarbonAwareSdkClient;
import com.snowfort.carbon.models.Emissions;
import com.snowfort.carbon.sci.SoftwareCarbonIntensity;
import com.snowfort.carbon.sci.SoftwareCarbonIntensityService;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarbonAwareActuatorEndpointTest {

    @Mock
    private CarbonAwareSdkClient client;

    @Mock
    private SoftwareCarbonIntensityService sciService;

    @Test
    public void testSciEndpoint() {
        MeterRegistry registry = new SimpleMeterRegistry();
        SoftwareCarbonIntensity sci = new SoftwareCarbonIntensity(1.0, 2.0, 3.0);
        when(sciService.getSoftwareCarbonIntensity()).thenReturn(sci);

        CarbonAwareActuatorEndpoint endpoint = new CarbonAwareActuatorEndpoint(client, registry, sciService);
        ResponseEntity<SoftwareCarbonIntensity> response = endpoint.sciEndpoint();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sci, response.getBody());
        assertEquals(5.0, registry.find("carbon.sci").gauge().value());
    }

    @Test
    public void testEmissionsEndpoint() {
        MeterRegistry registry = new SimpleMeterRegistry();
        List<Emissions> emissions = new ArrayList<>();
        Emissions e1 = new Emissions();
        e1.setRating(1.0);
        Emissions e2 = new Emissions();
        e2.setRating(2.0);
        emissions.add(e1);
        emissions.add(e2);
        when(client.emissionsForLocation()).thenReturn(emissions);

        CarbonAwareActuatorEndpoint endpoint = new CarbonAwareActuatorEndpoint(client, registry, sciService);
        ResponseEntity<List<Emissions>> response = endpoint.emissions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emissions, response.getBody());
        assertEquals(1.0, registry.find("carbon.emissions").gauge().value());
    }
}
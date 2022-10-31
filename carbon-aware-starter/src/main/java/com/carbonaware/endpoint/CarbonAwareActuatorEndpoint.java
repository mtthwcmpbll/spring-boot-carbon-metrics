package com.carbonaware.endpoint;

import com.carbonaware.apis.CarbonAwareSdkClient;
import com.carbonaware.models.Emissions;
import com.carbonaware.sci.SoftwareCarbonIntensity;
import com.carbonaware.sci.SoftwareCarbonIntensityService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestControllerEndpoint(id = "carbon")
public class CarbonAwareActuatorEndpoint {

    private CarbonAwareSdkClient client;
    private MeterRegistry registry;
    private SoftwareCarbonIntensityService sciService;

    public CarbonAwareActuatorEndpoint(CarbonAwareSdkClient client, MeterRegistry registry, SoftwareCarbonIntensityService sciService) {
        this.client = client;
        this.registry = registry;
        this.sciService = sciService;
        registerPrometheusEndpoints();
    }

    @GetMapping("/sci")
    public ResponseEntity<SoftwareCarbonIntensity> sciEndpoint() {
        return new ResponseEntity<>(sciService.getSoftwareCarbonIntensity(), HttpStatus.OK);
    }

    @GetMapping("/emissions")
    public ResponseEntity<List<Emissions>> emissions() {
        return new ResponseEntity<>(client.emissionsForLocation(), HttpStatus.OK);
    }

    private void registerPrometheusEndpoints() {
        Gauge.builder("carbon.sci", () -> sciEndpoint().getBody().getSoftwareCarbonIntensity()).register(registry);
        Gauge.builder("carbon.emissions", () -> {
            List<Emissions> emissions = emissions().getBody();
            return emissions.stream().map(e -> e.getRating()).findFirst().orElse(0.0);
        }).register(registry);
    }

}

package com.carbonaware.endpoint;

import com.carbonaware.apis.CarbonAwareSdkClient;
import com.carbonaware.models.Emissions;
import com.carbonaware.sci.SoftwareCarbonIntensity;
import com.carbonaware.sci.SoftwareCarbonIntensityService;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestControllerEndpoint(id = "carbon")
public class CarbonAwareActuatorEndpoint {

    private CarbonAwareSdkClient client;
    private SoftwareCarbonIntensityService sciService;

    public CarbonAwareActuatorEndpoint(CarbonAwareSdkClient client, SoftwareCarbonIntensityService sciService) {
        this.client = client;
        this.sciService = sciService;
    }

    @GetMapping("/emissions")
    public ResponseEntity<List<Emissions>> customEndPoint() {
        return new ResponseEntity<>(client.emissionsForLocation(), HttpStatus.OK);
    }

    @GetMapping("/sci")
    public ResponseEntity<SoftwareCarbonIntensity> sciEndpoint() {
        return new ResponseEntity<>(sciService.getSoftwareCarbonIntensity(), HttpStatus.OK);
    }

}

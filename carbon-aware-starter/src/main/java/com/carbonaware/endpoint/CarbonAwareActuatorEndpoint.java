package com.carbonaware.endpoint;

import com.carbonaware.apis.CarbonAwareSdkClient;
import com.carbonaware.models.Emissions;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestControllerEndpoint(id = "carbon")
public class CarbonAwareActuatorEndpoint {

    private CarbonAwareSdkClient client;

    public CarbonAwareActuatorEndpoint(CarbonAwareSdkClient client) {
        this.client = client;
    }

    @GetMapping("/emissions")
    public ResponseEntity<List<Emissions>> customEndPoint() {
        return new ResponseEntity<>(client.emissionsForLocation(), HttpStatus.OK);
    }

}

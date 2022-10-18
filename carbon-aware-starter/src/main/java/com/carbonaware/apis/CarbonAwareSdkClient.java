package com.carbonaware.apis;

import com.carbonaware.config.CarbonAwareProperties;
import com.carbonaware.models.Emissions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CarbonAwareSdkClient {

    private RestTemplate restTemplate;
    private CarbonAwareProperties props;

    public CarbonAwareSdkClient(RestTemplate restTemplate, CarbonAwareProperties props) {
        this.restTemplate = restTemplate;
        this.props = props;
    }

    public List<Emissions> emissionsForLocation(String location) {
        String url = props.getEndpoint() + "/emissions/bylocation?location=" + location;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<List<Emissions>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Emissions>>() {
                }
        );

        return response.getBody();
    }
}

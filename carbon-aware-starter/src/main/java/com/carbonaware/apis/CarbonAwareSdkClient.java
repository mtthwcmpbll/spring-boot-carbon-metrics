package com.carbonaware.apis;

import com.carbonaware.config.CarbonAwareProperties;
import com.carbonaware.models.Emissions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class CarbonAwareSdkClient {

    private RestTemplate restTemplate;
    private CarbonAwareProperties props;
    private CarbonEmissionsParams params;

    public CarbonAwareSdkClient(RestTemplate restTemplate, CarbonAwareProperties props, CarbonEmissionsParams params) {
        this.restTemplate = restTemplate;
        this.props = props;
        this.params = params;
    }

    public List<Emissions> emissionsForLocation() {
        String url = props.getEndpoint() + "/emissions/bylocation?location=" + params.getLocation();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<List<Emissions>> response = restTemplate.exchange(url, GET, request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }
}

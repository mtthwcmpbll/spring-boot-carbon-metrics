package com.snowfort.carbon.apis;

import com.snowfort.carbon.config.CarbonAwareProperties;
import com.snowfort.carbon.models.Emissions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.GET;

@ExtendWith(MockitoExtension.class)
class CarbonAwareSdkClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testEmissionsForLocation() {
        CarbonAwareProperties props = new CarbonAwareProperties();
        props.setLocation("uswest2");
        props.setEndpoint("http://localhost:3030");

        CarbonEmissionsParams params = new DefaultCarbonEmissionsParams("uswest2");

        List<Emissions> expectedEmissions = new ArrayList<>();
        Emissions emission = new Emissions();
        emission.setLocation("uswest2");
        emission.setDuration("10");
        emission.setRating(10.124);
        expectedEmissions.add(emission);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);

        CarbonAwareSdkClient sdkClient = new CarbonAwareSdkClient(restTemplate, props, params);

        when(restTemplate.exchange(eq("http://localhost:3030/emissions/bylocation?location=uswest2"),
                eq(GET), eq(request), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(expectedEmissions));

        List<Emissions> actualEmissions = sdkClient.emissionsForLocation();

        Assertions.assertEquals(expectedEmissions, actualEmissions);
    }

}
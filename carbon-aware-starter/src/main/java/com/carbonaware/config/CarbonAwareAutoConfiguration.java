package com.carbonaware.config;

import com.carbonaware.apis.CarbonAwareSdkClient;
import com.carbonaware.endpoint.CarbonAwareActuatorEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@ConditionalOnProperty(prefix = "spring.carbon-aware", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(CarbonAwareProperties.class)
public class CarbonAwareAutoConfiguration {

    @Bean
    public RestTemplate carbonAwareRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CarbonAwareSdkClient carbonAwareSdkClient(RestTemplate carbonAwareRestTemplate, CarbonAwareProperties props) {
        return new CarbonAwareSdkClient(carbonAwareRestTemplate, props);
    }

    @Bean
    public CarbonAwareActuatorEndpoint endpoint(CarbonAwareSdkClient client) {
        return new CarbonAwareActuatorEndpoint(client);
    }
}

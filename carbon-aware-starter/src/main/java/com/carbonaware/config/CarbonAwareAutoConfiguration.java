package com.carbonaware.config;

import com.carbonaware.apis.CarbonAwareSdkClient;
import com.carbonaware.apis.CarbonEmissionsParams;
import com.carbonaware.apis.DefaultCarbonEmissionsParams;
import com.carbonaware.endpoint.CarbonAwareActuatorEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @ConditionalOnMissingBean(CarbonEmissionsParams.class)
    @Bean
    public DefaultCarbonEmissionsParams defaultCarbonEmissionsParams(CarbonAwareProperties props) {
        return new DefaultCarbonEmissionsParams(props.getLocation());
    }

    @Bean
    public CarbonAwareSdkClient carbonAwareSdkClient(RestTemplate carbonAwareRestTemplate, CarbonAwareProperties props, CarbonEmissionsParams params) {
        return new CarbonAwareSdkClient(carbonAwareRestTemplate, props, params);
    }

    @Bean
    public CarbonAwareActuatorEndpoint endpoint(CarbonAwareSdkClient client) {
        return new CarbonAwareActuatorEndpoint(client);
    }
}

package com.carbonaware.config;

import com.carbonaware.apis.CarbonAwareSdkClient;
import com.carbonaware.apis.CarbonEmissionsParams;
import com.carbonaware.apis.DefaultCarbonEmissionsParams;
import com.carbonaware.endpoint.CarbonAwareActuatorEndpoint;
import com.carbonaware.sci.*;
import io.micrometer.core.instrument.MeterRegistry;
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
    public CarbonAwareActuatorEndpoint endpoint(CarbonAwareSdkClient client, SoftwareCarbonIntensityService sciService) {
        return new CarbonAwareActuatorEndpoint(client, sciService);
    }

    @Bean
    public SoftwareCarbonIntensityService softwareCarbonIntensityService(EnergyConsumptionProvider energyConsumptionProvider,
                                                                         MarginalEmissionsProvider marginalEmissionsProvider,
                                                                         EmbodiedEmissionsProvider embodiedEmissionsProvider) {
        return new SoftwareCarbonIntensityService(energyConsumptionProvider, marginalEmissionsProvider, embodiedEmissionsProvider);
    }

    @Bean
    public EnergyConsumptionProvider energyConsumptionProvider(MeterRegistry metricsRegistry) {
        return new ResourceUtilizationEnergyConsumptionProvider(metricsRegistry);
    }

    @Bean
    public MarginalEmissionsProvider marginalEmissionsProvider(CarbonAwareSdkClient client) {
        return new CarbonAwareSdkMarginalEmissionsProvider(client);
    }

    @Bean
    public EmbodiedEmissionsProvider embodiedEmissionsProvider() {
        return new ConfiguredEmboddiedEmissionsProvider();
    }
}

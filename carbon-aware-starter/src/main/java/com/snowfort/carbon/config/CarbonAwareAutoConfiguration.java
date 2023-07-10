package com.snowfort.carbon.config;

import com.snowfort.carbon.apis.CarbonAwareSdkClient;
import com.snowfort.carbon.apis.CarbonEmissionsParams;
import com.snowfort.carbon.apis.DefaultCarbonEmissionsParams;
import com.snowfort.carbon.endpoint.CarbonAwareActuatorEndpoint;
import com.snowfort.carbon.sci.*;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@ConditionalOnProperty(prefix = "spring.carbon-aware", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(CarbonAwareProperties.class)
@Import(OshiAutoConfiguration.class)
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
    public CarbonAwareActuatorEndpoint endpoint(CarbonAwareSdkClient client, MeterRegistry registry, SoftwareCarbonIntensityService sciService) {
        return new CarbonAwareActuatorEndpoint(client, registry, sciService);
    }

    @Bean
    public SoftwareCarbonIntensityService softwareCarbonIntensityService(EnergyConsumptionProvider energyConsumptionProvider,
                                                                         MarginalEmissionsProvider marginalEmissionsProvider,
                                                                         EmbodiedEmissionsProvider embodiedEmissionsProvider) {
        return new SoftwareCarbonIntensityService(energyConsumptionProvider, marginalEmissionsProvider, embodiedEmissionsProvider);
    }

    @ConditionalOnMissingBean(EnergyConsumptionProvider.class)
    @Bean
    public EnergyConsumptionProvider energyConsumptionProvider(MeterRegistry metricsRegistry) {
        return new ResourceUtilizationEnergyConsumptionProvider(metricsRegistry);
    }

    @ConditionalOnMissingBean(MarginalEmissionsProvider.class)
    @Bean
    public MarginalEmissionsProvider marginalEmissionsProvider(CarbonAwareSdkClient client) {
        return new CarbonAwareSdkMarginalEmissionsProvider(client);
    }

    @ConditionalOnMissingBean(EmbodiedEmissionsProvider.class)
    @Bean
    public EmbodiedEmissionsProvider embodiedEmissionsProvider(CarbonAwareProperties props) {
        return new ConfiguredEmbodiedEmissionsProvider(props.getEmbodiedEmissions());
    }
}

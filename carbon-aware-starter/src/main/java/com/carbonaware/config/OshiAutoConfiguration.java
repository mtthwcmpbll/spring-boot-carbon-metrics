package com.carbonaware.config;

import com.carbonaware.apis.CarbonAwareSdkClient;
import com.carbonaware.apis.CarbonEmissionsParams;
import com.carbonaware.apis.DefaultCarbonEmissionsParams;
import com.carbonaware.endpoint.CarbonAwareActuatorEndpoint;
import com.carbonaware.sci.*;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import oshi.hardware.HardwareAbstractionLayer;

@ConditionalOnProperty(prefix = "spring.carbon-aware", name = "enabled", matchIfMissing = true)
//@AutoConfiguration(after = CarbonAwareAutoConfiguration.class)
@ConditionalOnClass({ HardwareAbstractionLayer.class })
@ConditionalOnMissingBean({ EnergyConsumptionProvider.class })
public class OshiAutoConfiguration {

    @Bean
    public EnergyConsumptionProvider oshiHardwarePowerUtilizationEnergyConsumptionProvider() {
        return new PowerUtilizationEnergyConsumptionProvider();
    }

}

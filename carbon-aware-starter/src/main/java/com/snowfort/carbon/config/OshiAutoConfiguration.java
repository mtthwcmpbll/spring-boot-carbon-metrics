package com.snowfort.carbon.config;

import com.snowfort.carbon.sci.EnergyConsumptionProvider;
import com.snowfort.carbon.sci.PowerUtilizationEnergyConsumptionProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
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

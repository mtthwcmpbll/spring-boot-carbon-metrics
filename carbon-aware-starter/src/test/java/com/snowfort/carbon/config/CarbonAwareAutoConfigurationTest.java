package com.snowfort.carbon.config;

import com.snowfort.carbon.sci.EnergyConsumptionProvider;
import com.snowfort.carbon.sci.PowerUtilizationEnergyConsumptionProvider;
import com.snowfort.carbon.sci.ResourceUtilizationEnergyConsumptionProvider;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.hardware.HardwareAbstractionLayer;

import static org.assertj.core.api.Assertions.assertThat;

class CarbonAwareAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(CarbonAwareAutoConfiguration.class));

    @Configuration(proxyBeanMethods = false)
    static class MicrometerConfiguration {

        @Bean
        MeterRegistry metricsRegistry() {
            return new SimpleMeterRegistry();
        }

    }

    @Test
    void defaultEnergyConsumptionProvider() {
        this.contextRunner
                .withPropertyValues("spring.carbon-aware.enabled=true")
                .withUserConfiguration(MicrometerConfiguration.class)
                .withClassLoader(new FilteredClassLoader(HardwareAbstractionLayer.class))
                .run((context) -> {
            assertThat(context).hasSingleBean(EnergyConsumptionProvider.class);
            assertThat(context).getBean(EnergyConsumptionProvider.class).isInstanceOf(ResourceUtilizationEnergyConsumptionProvider.class);
        });
    }

    @Test
    void oshiEnergyConsumptionProvider() {
        this.contextRunner
                .withPropertyValues("spring.carbon-aware.enabled=true")
                .withUserConfiguration(MicrometerConfiguration.class)
                .run((context) -> {
                    assertThat(context).hasSingleBean(EnergyConsumptionProvider.class);
                    assertThat(context).getBean(EnergyConsumptionProvider.class).isInstanceOf(PowerUtilizationEnergyConsumptionProvider.class);
                });
    }

}
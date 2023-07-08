package com.carbonaware.config;

import com.carbonaware.sci.EnergyConsumptionProvider;
import com.carbonaware.sci.PowerUtilizationEnergyConsumptionProvider;
import com.carbonaware.sci.ResourceUtilizationEnergyConsumptionProvider;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.hardware.HardwareAbstractionLayer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
package com.snowfort.carbon.sci;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;

public class ResourceUtilizationEnergyConsumptionProvider implements EnergyConsumptionProvider {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ResourceUtilizationEnergyConsumptionProvider.class);

    private final MeterRegistry metricsRegistry;

    // This is hard-coded for now, but could be pulled from system-specific models similar to the way MacOs' Activity
    // Monitor does it in modern versions today.  See https://blog.mozilla.org/nnethercote/2015/08/26/what-does-the-os-x-activity-monitors-energy-impact-actually-measure/comment-page-1/#comment-46775
    private final double cpuEnergyTax = 100.0;

    public ResourceUtilizationEnergyConsumptionProvider(MeterRegistry metricsRegistry) {
        this.metricsRegistry = metricsRegistry;
    }

    @Override
    public double getEnergyConsumption() {
        // it's difficult to tell what kind of meter a metric is from the endpoint
        metricsRegistry.forEachMeter(meter -> {
            log.info("Found " + meter.getId() + " metric of type " + meter.getClass().getName());
        });

        // The process CPU usages is a percentage 0.0 - 1.0 of the systems processors, so we'll need to multiply this by
        // the systems total processors to get a scalar value that will change when we reduce the CPUs provisioned to an
        // application.
        Gauge cpuUsageSearch = metricsRegistry.find("process.cpu.usage").gauge();
        if (cpuUsageSearch == null) {
            log.error("Unable to find process.cpu.usage metric, returning 0 for energy consumption");
            return 0.0;
        }

        Gauge systemCpusAvailableSearch = metricsRegistry.find("system.cpu.count").gauge();
        if (systemCpusAvailableSearch == null) {
            log.error("Unable to find system.cpu.count metric, returning 0 for energy consumption");
            return 0.0;
        }

        double processCpuUsagePercentage = cpuUsageSearch.value();
        log.info("Current process CPU usage is: " + processCpuUsagePercentage);
        double systemCpusAvailable = systemCpusAvailableSearch.value();
        log.info("Total CPUs on system is: " + systemCpusAvailable);

        return (processCpuUsagePercentage * systemCpusAvailable) * cpuEnergyTax;
    }
}

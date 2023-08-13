package com.snowfort.carbon.sci;

/**
 * The Software Carbon Intensity (SCI) is a rate, carbon emissions per one unit of R. The equation used to calculate
 * the SCI value of a software system is:
 *
 * SCI = ((E * I) + M) per R
 */
public class SoftwareCarbonIntensityService {

    // In the SCI spec, this provides the 'E' value
    EnergyConsumptionProvider energyConsumptionProvider;

    // In the SCI spec, this provides the 'I' value
    MarginalEmissionsProvider marginalEmissionsProvider;

    // In the SCI spec, this provides the 'M' value
    EmbodiedEmissionsProvider embodiedEmissionsProvider;

    public SoftwareCarbonIntensityService(
            EnergyConsumptionProvider energyConsumptionProvider,
            MarginalEmissionsProvider marginalEmissionsProvider,
            EmbodiedEmissionsProvider embodiedEmissionsProvider) {
        this.energyConsumptionProvider = energyConsumptionProvider;
        this.marginalEmissionsProvider = marginalEmissionsProvider;
        this.embodiedEmissionsProvider = embodiedEmissionsProvider;
    }

    public SoftwareCarbonIntensity getSoftwareCarbonIntensity() {
        return new SoftwareCarbonIntensity(
                energyConsumptionProvider.getEnergyConsumption(),
                marginalEmissionsProvider.getMarginalEmissions(),
                embodiedEmissionsProvider.getEmbodiedEmissions());
    }

}

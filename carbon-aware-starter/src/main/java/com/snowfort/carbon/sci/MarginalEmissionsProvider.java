package com.snowfort.carbon.sci;

/*
 * From the Software Carbon Intensity (SCI) specification: https://grnsft.org/sci
 *
 * The carbon intensity of electricity is a measure of how much carbon (CO2eq) emissions are produced per
 * kilowatt-hour (kWh) of electricity consumed. This MUST be in grams of carbon per kilowatt hours (gCO2eq/kWh).
 *
 * Because this standard uses a consequential approach, marginal emissions rates MUST be used for electricity
 * consumption. Location-based measures the grid carbon intensity of a regional balancing authority. From a developer
 * perspective, only the location-based info is important for having an impact on reducing carbon emissions. This
 * excludes market-based measures, and is distinct from 100% renewable energy claims.
 *
 * The only figure that matters if you’re trying to optimize the scheduling of your compute in real-time is the
 * marginal emissions intensity. This is the emissions intensity of the marginal power plant which will be turned up if
 * you schedule some compute (e.g. increase electricity demand from the grid) at that moment.
 */
public interface MarginalEmissionsProvider {

    /**
     * The carbon intensity of electricity is a measure of how much carbon (CO2eq) emissions are produced per
     * kilowatt-hour (kWh) of electricity consumed. This MUST be in grams of carbon per kilowatt hours (gCO2eq/kWh).
     *
     * @return The marginal emissions at a given location in grams of carbon per kilowatt hours (gCO2eq/kWh).
     */
    double getMarginalEmissions();

}

# Spring Boot Carbon-Aware Metrics

## The Problem

This project has three goals:

1. Make it easy to measure and see the carbon impact of Spring Boot applications, while they're running and while they're doing specific work
2. Make it easy to measure and see that impact across multiple services interacting with each other
3. Make it easy for lots of developers to get that same data and measure improvements towards their sustainability goals

## Carbon Aware Starter

The carbon-aware-starter lies at the heart to this project's offering. It provides the following actuator endpoints and prometheus metrics out of the box to monitor the spring boot apps.

| Sl. | Actuator Endpoint          | Prometheus metrics | Description                                                                                         |
|-----|----------------------------|--------------------|-----------------------------------------------------------------------------------------------------|
| 1   | /actuator/carbon/emissions | carbon_emissions   | It provides the marginal carbon intensity from the Carbon-Aware SDK for the application's location. |
| 2   | /actuator/carbon/sci       | carbon_sci         | It provides SCI (Software Carbon Intensity) score of the application                                |

The sci score is calculated by a formulae `SCI = ((E * I) + M)`. For this calculation the following interfaces are provided :
* `EnergyConsumptionProvider (E)`: This interface defines the contract for adding the energy consumed by a software system.
* `MarginalEmissionsProvider (I)`: This interface defines the contract for adding the location-based marginal carbon intensity.
* `EmbodiedEmissionsProvider (M)`: This interface defines the contract for adding the embodied emissions of the hardware needed to operate a software system.

The starter also provides out-of-the-box implementations for each of the above interfaces, but they can also be easily overidden if an implementation is added for these interfaces in the spring boot app which is using this starter. The default implementations are as follows :
* `ResourceUtilizationEnergyConsumptionProvider` : This uses the underlying cpu usage & memory usage along with an energy tax to determine an energy consumption.
* `ConfiguredEmboddiedEmissionsProvider` : This uses a configurable value based on the underlying hardware.
*  `CarbonAwareSdkMarginalEmissionsProvider` : This fetches the marginal emissions from the carbon aware sdk

The starter provides the following properties which needs to be configured for using:

| Sl. | Property                              | Description                                                                                                   |
|-----|---------------------------------------|---------------------------------------------------------------------------------------------------------------|
| 1   | spring.carbon-aware.enabled           | This property needs to be set as true to enable the starter. The default value is false.                      |
| 2   | spring.carbon-aware.endpoint          | This property points to base endpoint of carbon-aware-sdk. A value neeeds to be passed if starter is enabled. |
| 3   | spring.carbon-aware.location          | This property points to location for which the emissions are calculated. Default value is "".                 |
| 4   | spring.carbon-aware.embodiedEmissions | This property points to the emboided emissions for the hardware. Default value is 0.0                         |               |

## Quickstart

In the same example there are two services `hello-service` and `weather-service` where we have added the `carbon-aware-starter`. To start the project do the following steps :

- Ensure that java-17 is installed in your machine
- Create a `.env` file having the following properties :
```
  WATTTIME_CLIENT_USERNAME=<watttime_username>
  WATTTIME_CLIENT_PASSWORD=<watttime_password>
  EMISSIONS_LOCATION=<emissions_location>
  ```
- Build the application docker containers with `./playground.sh`
- Start the docker containers (along with the third-party collectors) with `docker-compose up -d --build`
- To visualise on grafana dashboards, use prometheus as a datasource and use `carbon_emissions` and `carbon_sci` as metrics in panels.

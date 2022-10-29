package com.carbonaware.sci;

import java.util.Random;

public class ResourceUtilizationEnergyConsumptionProvider implements EnergyConsumptionProvider {


    public ResourceUtilizationEnergyConsumptionProvider() {

    }

    @Override
    public double getEnergyConsumption() {
        // TODO: placeholder
        return new Random().nextDouble() * 100;
    }
}

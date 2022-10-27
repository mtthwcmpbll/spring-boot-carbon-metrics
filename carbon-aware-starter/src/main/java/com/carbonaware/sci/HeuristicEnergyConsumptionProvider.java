package com.carbonaware.sci;

import java.util.Random;

public class HeuristicEnergyConsumptionProvider implements EnergyConsumptionProvider {


    public HeuristicEnergyConsumptionProvider() {

    }

    @Override
    public double getEnergyConsumption() {
        // TODO: placeholder
        return new Random().nextDouble() * 100;
    }
}

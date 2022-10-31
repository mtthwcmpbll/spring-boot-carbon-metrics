package com.example.hello;

import com.carbonaware.sci.EmbodiedEmissionsProvider;
import org.springframework.stereotype.Component;

@Component
public class SampleEmbodiedEmissions implements EmbodiedEmissionsProvider {

    @Override
    public double getEmbodiedEmissions() {
        return 10.0;
    }
}

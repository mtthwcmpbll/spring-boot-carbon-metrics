package com.example.hello;

import com.snowfort.carbon.sci.EmbodiedEmissionsProvider;
import org.springframework.stereotype.Component;

@Component
public class SampleEmbodiedEmissions implements EmbodiedEmissionsProvider {

    @Override
    public double getEmbodiedEmissions() {
        return 10.0;
    }
}

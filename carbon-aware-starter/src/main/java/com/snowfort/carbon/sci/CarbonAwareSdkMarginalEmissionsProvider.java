package com.snowfort.carbon.sci;

import com.snowfort.carbon.apis.CarbonAwareSdkClient;
import com.snowfort.carbon.models.Emissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

public class CarbonAwareSdkMarginalEmissionsProvider implements MarginalEmissionsProvider {

    private static final Logger log = LoggerFactory.getLogger(CarbonAwareSdkMarginalEmissionsProvider.class);


    private CarbonAwareSdkClient client;

    public CarbonAwareSdkMarginalEmissionsProvider(CarbonAwareSdkClient client) {
        this.client = client;
    }

    @Override
    public double getMarginalEmissions() {
        List<Emissions> emissions = client.emissionsForLocation();
        // sort the emissions by time, and then filter
        final long now = System.currentTimeMillis();
        Emissions closestEmissionsData = emissions.stream().min(new Comparator<Emissions>() {
            public int compare(Emissions e1, Emissions e2) {
                long diff1 = Math.abs(e1.getTime().toInstant().toEpochMilli() - now);
                long diff2 = Math.abs(e2.getTime().toInstant().toEpochMilli() - now);
                return Long.compare(diff1, diff2);
            }
        }).orElse(null);

        // TODO: think about error handling for this
        if (closestEmissionsData == null) {
            log.error("There was a problem getting emissions data from Carbon Aware SDK");
            return 0.0;
        }

        return closestEmissionsData.getRating();
    }
}
package com.Chakradhar.reint_wind.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Chakradhar.reint_wind.model.ForecastGeneration;

@Service
public class ForecastProcessingService {

    public Double findBestForecast(
            LocalDateTime targetTime,
            int horizon,
            List<ForecastGeneration> forecasts
    ) {

        LocalDateTime cutoff = targetTime.minusHours(horizon);
        System.out.println("Target: " + targetTime);
        return forecasts.stream()
                .filter(f -> f.getStartTime().withSecond(0).withNano(0)
                		.equals(targetTime.withSecond(0).withNano(0)))
                .filter(f -> !f.getPublishTime().isAfter(cutoff))
                .max(Comparator.comparing(ForecastGeneration::getPublishTime))
                .map(ForecastGeneration::getGeneration)
                .orElse(null);
    }
}

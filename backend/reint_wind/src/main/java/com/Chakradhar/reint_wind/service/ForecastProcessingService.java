package com.Chakradhar.reint_wind.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        // Must be published at least "horizon" hours before the target time
        LocalDateTime cutoff = targetTime.minusHours(horizon);
        
        LocalDateTime targetHour = targetTime.truncatedTo(ChronoUnit.HOURS);

        return forecasts.stream()
                .filter(f -> f.getStartTime().truncatedTo(ChronoUnit.HOURS).equals(targetHour))
                .filter(f -> !f.getPublishTime().isAfter(cutoff))
                .max(Comparator.comparing(ForecastGeneration::getPublishTime))
                .map(ForecastGeneration::getGeneration)
                .orElse(null);
    }
}
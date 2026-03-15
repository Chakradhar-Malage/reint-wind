package com.Chakradhar.reint_wind.controller;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Chakradhar.reint_wind.model.ActualGeneration;
import com.Chakradhar.reint_wind.model.ForecastGeneration;
import com.Chakradhar.reint_wind.model.WindDataResponse;
import com.Chakradhar.reint_wind.model.WindErrorResponse;
import com.Chakradhar.reint_wind.service.ActualGenerationService;
import com.Chakradhar.reint_wind.service.ForecastGenerationService;
import com.Chakradhar.reint_wind.service.ForecastProcessingService;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class WindDataController {

    private final ActualGenerationService actualService;
    private final ForecastGenerationService forecastService;
    private final ForecastProcessingService processingService;

    public WindDataController(
            ActualGenerationService actualService,
            ForecastGenerationService forecastService,
            ForecastProcessingService processingService
    ) {
        this.actualService = actualService;
        this.forecastService = forecastService;
        this.processingService = processingService;
    }

    @GetMapping("/wind-data")
    public Mono<List<WindDataResponse>> getWindData(@RequestParam int horizon){
        Mono<List<ActualGeneration>> actualsMono = actualService.fetchActualGeneration();
        Mono<List<ForecastGeneration>> forecastsMono = forecastService.fetchForecastData();

        return Mono.zip(actualsMono, forecastsMono)
                .map(tuple -> {

                    List<ActualGeneration> actuals = tuple.getT1();
                    List<ForecastGeneration> forecasts = tuple.getT2();
                    System.out.println("Fetched " + actuals.size() + " actual records.");
                    System.out.println("Fetched " + forecasts.size() + " forecast records.");
                    List<WindDataResponse> response = new ArrayList<>();
                    if (!forecasts.isEmpty()) {
                        System.out.println("First forecast date is: " + forecasts.get(0).getStartTime());
                    }
                    
                    actuals.sort(Comparator.comparing(ActualGeneration::getStartTime));
                    
                    for (ActualGeneration actual : actuals) {
                        Double forecast = processingService.findBestForecast(
                                actual.getStartTime(),
                                horizon,
                                forecasts
                        );

                        response.add(new WindDataResponse(
                                actual.getStartTime(),
                                actual.getGeneration(),
                                forecast
                        ));
                    }

                    return response;
               
                });
    }
  
    @GetMapping("/wind-error")
    public Mono<List<WindErrorResponse>> getWindError(@RequestParam int horizon) {

        Mono<List<ActualGeneration>> actualsMono = actualService.fetchActualGeneration();
        Mono<List<ForecastGeneration>> forecastsMono = forecastService.fetchForecastData();

        return Mono.zip(actualsMono, forecastsMono)
                .map(tuple -> {

                    List<ActualGeneration> actuals = tuple.getT1();
                    List<ForecastGeneration> forecasts = tuple.getT2();

                    List<WindErrorResponse> response = new ArrayList<>();

                    for (ActualGeneration actual : actuals) {

                        Double forecast = processingService.findBestForecast(
                                actual.getStartTime(),
                                horizon,
                                forecasts
                        );

                        if (forecast != null) {

                            Double error = actual.getGeneration() - forecast;

                            response.add(new WindErrorResponse(
                                    actual.getStartTime(),
                                    actual.getGeneration(),
                                    forecast,
                                    error
                            ));
                        }
                    }

                    return response;
                });
    }

}
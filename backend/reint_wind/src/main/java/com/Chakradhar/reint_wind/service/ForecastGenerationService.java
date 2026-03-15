package com.Chakradhar.reint_wind.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.Chakradhar.reint_wind.model.ForecastGeneration;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class ForecastGenerationService {

    private final WebClient webClient;

    public ForecastGenerationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<ForecastGeneration>> fetchForecastData() {
        // Fetch from Dec 25th to guarantee we have all forecasts published in advance
        String fromTime = "2023-12-25T00:00:00Z"; 
        String toTime = "2024-01-03T23:30:00Z";
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bmrs/api/v1/datasets/WINDFOR/stream") 
                        .queryParam("publishDateTimeFrom", fromTime)
                        .queryParam("publishDateTimeTo", toTime)
                        .queryParam("format", "json")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ForecastGeneration.class)
                .collectList()
                .onErrorResume(e -> Mono.just(List.of()));
    }
}
package com.Chakradhar.reint_wind.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.Chakradhar.reint_wind.model.ActualGeneration;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class ActualGenerationService {

    private final WebClient webClient;

    public ActualGenerationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<ActualGeneration>> fetchActualGeneration() {
        // Fetch exactly 3 days of Actuals
        String fromDate = "2024-01-01";
        String toDate = "2024-01-03";
        
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bmrs/api/v1/datasets/FUELHH/stream")
                        .queryParam("settlementDateFrom", fromDate) 
                        .queryParam("settlementDateTo", toDate)    
                        .queryParam("fuelType", "WIND") 
                        .queryParam("format", "json")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ActualGeneration.class)
                .collectList()
                .onErrorResume(e -> Mono.just(List.of()));
    }
}
package com.Chakradhar.reint_wind.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Chakradhar.reint_wind.model.ActualGeneration;
import com.Chakradhar.reint_wind.model.ElexonResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ActualGenerationService {

    private final WebClient webClient;

    public ActualGenerationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<ActualGeneration>> fetchActualGeneration() {
        // Use the exact same 48-hour window as your forecast service
//        String fromTime = LocalDateTime.now().minusDays(2).atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT);
//        String toTime = LocalDateTime.now().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT);
    	String fromTime = "2024-01-01T00:00:00Z";
    	String toTime = "2024-01-31T23:30:00Z";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bmrs/api/v1/datasets/FUELHH") // Half-hourly data
                        .queryParam("startTimeFrom", fromTime)
                        .queryParam("startTimeTo", toTime)
                        .queryParam("fuelType", "WIND") // <-- CRITICAL: Only get wind data
                        .queryParam("format", "json")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ElexonResponse<ActualGeneration>>() {})
                .map(response -> response.getData() != null ? response.getData() : List.<ActualGeneration>of())
                .onErrorResume(e -> {
                    System.err.println("Actual Gen Error: " + e.getMessage());
                    e.printStackTrace();
                    return Mono.just(List.of());
                });
    }
}
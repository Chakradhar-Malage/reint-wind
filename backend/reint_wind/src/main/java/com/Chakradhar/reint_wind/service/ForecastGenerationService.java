package com.Chakradhar.reint_wind.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.Chakradhar.reint_wind.model.ForecastGeneration;
import com.Chakradhar.reint_wind.model.ElexonResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ForecastGenerationService {

    private final WebClient webClient;

    public ForecastGenerationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<ForecastGeneration>> fetchForecastData() {
//        String fromTime = LocalDateTime.now().minusDays(2).atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT);
//        String toTime = LocalDateTime.now().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT);
    	String fromTime = "2024-01-01T00:00:00Z";
    	String toTime = "2024-01-31T23:30:00Z";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/bmrs/api/v1/datasets/WINDFOR")
                        .queryParam("startTimeFrom", fromTime)
                        .queryParam("startTimeTo", toTime)
                        .queryParam("format", "json")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                // Map the JSON to our wrapper, then extract the list!
                .bodyToMono(new ParameterizedTypeReference<ElexonResponse<ForecastGeneration>>() {})
                .map(response -> response.getData() != null ? response.getData() : List.<ForecastGeneration>of())
                .onErrorResume(e -> {
                    System.err.println("Forecast Error: " + e.getMessage());
                    return Mono.just(List.of());
                });
    }
}
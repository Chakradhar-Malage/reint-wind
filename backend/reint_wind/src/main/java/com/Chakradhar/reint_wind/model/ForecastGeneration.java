package com.Chakradhar.reint_wind.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForecastGeneration {

    @JsonProperty("startTime")
    private LocalDateTime startTime;

    @JsonProperty("publishTime")
    private LocalDateTime publishTime;

    @JsonProperty("generation")
    private Double generation;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public Double getGeneration() {
        return generation;
    }

    public void setGeneration(Double generation) {
        this.generation = generation;
    }
}
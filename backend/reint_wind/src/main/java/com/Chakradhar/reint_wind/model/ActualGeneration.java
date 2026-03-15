package com.Chakradhar.reint_wind.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActualGeneration {
    private LocalDateTime startTime;
    private Double generation;
}
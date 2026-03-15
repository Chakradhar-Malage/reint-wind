package com.Chakradhar.reint_wind.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WindDataResponse {
    private LocalDateTime time;
    private Double actual;
    private Double forecast;
}
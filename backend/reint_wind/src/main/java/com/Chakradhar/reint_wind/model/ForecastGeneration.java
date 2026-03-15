package com.Chakradhar.reint_wind.model;

import java.time.LocalDateTime;
import lombok.Data;

//@Data
public class ForecastGeneration {
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
	private LocalDateTime startTime;
    private LocalDateTime publishTime;
    private Double generation;
}
package com.inpress.optimusweather.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("timezone")
    private String timeZone;
    @JsonProperty("temperature")
    private double temperature;
    @JsonProperty("windspeed")
    private double windSpeed;
    @JsonProperty("winddirection")
    private int windDirection;
    @JsonProperty("weathercode")
    private WeatherCode weatherCode;
    @JsonProperty("is_day")
    private int is_day;
    @JsonProperty("time")
    private String time;
}

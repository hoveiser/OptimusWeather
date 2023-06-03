package com.inpress.optimusweather.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastWeatherDto {
    private Double latitude;
    private Double longitude;
    private Set<WeatherDto> weatherDtos=new HashSet<>();
}

package com.inpress.optimusweather.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchWeatherDto {
    private Double latitude;
    private Double longitude;
    private SortedSet<WeatherDto> weatherDtos=new TreeSet<>();
}

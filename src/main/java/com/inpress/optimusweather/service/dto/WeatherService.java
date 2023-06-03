package com.inpress.optimusweather.service.dto;

import com.inpress.optimusweather.web.remote.OpenMeteoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final OpenMeteoClient openMeteoClient;

    public WeatherDto getCurrentWeather(Double latitude, Double longitude){
        return openMeteoClient.currentWeather(latitude,longitude);
    }

    public ForecastWeatherDto getForecastWeather(Long forecastDays,Double longitude,Double latitude){
        return null;
    }

    public HistoricalWeatherDto getHistoricalWeather(Long startDate,Long endDate,Double longitude,Double latitude){
        return null;
    }


}

package com.inpress.optimusweather.web.remote;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.inpress.optimusweather.service.dto.WeatherDto;
import com.inpress.optimusweather.service.dto.ForecastWeatherDto;
import com.inpress.optimusweather.service.dto.HistoricalWeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Deprecated
public class WeatherApiClient extends OptimusWeatherAPI {

    @Value("${wsn.weatherapi}")
    private String weatherApiUrl;

    private static final String CURRENT_WEATHER_URL = "";
    private static final String FORECAST_WEATHER_URL = "";
    private static final String HISTORICAL_WEATHER_URL = "";

    public WeatherApiClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper);
    }


    @Override
    WeatherDto currentWeather(Double longitude, Double latitude) {
        return null;
    }

    @Override
    ForecastWeatherDto forecastWeather(Long forecastDays, Double longitude, Double latitude) {
        return null;
    }

    @Override
    HistoricalWeatherDto historicalWeather(Long startDate, Long endDate, Double longitude, Double latitude) {
        return null;
    }
}

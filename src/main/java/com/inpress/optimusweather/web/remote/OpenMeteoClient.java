package com.inpress.optimusweather.web.remote;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inpress.optimusweather.service.dto.WeatherDto;
import com.inpress.optimusweather.service.dto.ForecastWeatherDto;
import com.inpress.optimusweather.service.dto.HistoricalWeatherDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OpenMeteoClient extends OptimusWeatherAPI {

    @Value("${wsn.openmeteo}")
    private String openMeteoUrl;



    private static final String CURRENT_WEATHER_URL = "?latitude={latitude}&longitude={longitude}&current_weather=true";
    private static final String FORECAST_WEATHER_URL = "?latitude={latitude}&longitude={longitude}&forecast_days={forecastDay}&&daily=temperature_2m_max,weathercode,windspeed_10m_max,winddirection_10m_dominant&timezone=GMT";
    private static final String HISTORICAL_WEATHER_URL = "";

    public OpenMeteoClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper);
    }


    @SneakyThrows
    @Override
    public WeatherDto currentWeather(Double latitude, Double longitude) {
        Map<String, String> params = new HashMap<>();
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        String response = restGet(
                UriComponentsBuilder.
                        fromHttpUrl(openMeteoUrl + CURRENT_WEATHER_URL).
                        buildAndExpand(params).
                        toUriString()
        );
        JsonNode jsonNode = objectMapper.readTree(response);
        WeatherDto currentWeather = objectMapper.readValue(jsonNode.get("current_weather").toString(), WeatherDto.class);
        currentWeather.setLongitude(jsonNode.get("longitude").asDouble());
        currentWeather.setLongitude(jsonNode.get("latitude").asDouble());
        currentWeather.setTimeZone(jsonNode.get("timezone").asText());
        return currentWeather;
    }

    @Override
    public ForecastWeatherDto forecastWeather(Long forecastDays,Double latitude,Double longitude) {
        Map<String, String> params = new HashMap<>();
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("forecastDay", String.valueOf(forecastDays));
        String response = restGet(
                UriComponentsBuilder.
                        fromHttpUrl(openMeteoUrl + FORECAST_WEATHER_URL).
                        buildAndExpand(params).
                        toUriString()
        );

        return null;
    }

    @Override
    public HistoricalWeatherDto historicalWeather(Long startDate, Long endDate,Double latitude,Double longitude) {
        return null;
    }


}

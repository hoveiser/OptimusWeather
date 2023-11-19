package com.inpress.optimusweather.web.rest.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.inpress.optimusweather.service.dto.WeatherDto;
import com.inpress.optimusweather.service.dto.BatchWeatherDto;
import com.inpress.optimusweather.service.dto.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Slf4j
//@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @SneakyThrows
    @GetMapping("openmeteo/{latitude}/{longitude}")
    public WeatherDto getCurrentWeatherByOpenMeteo(@PathVariable Double latitude, @PathVariable Double longitude) {
        log.debug("Request received. Find current weather data by open meteo. latitude : {},longitude : {}", latitude, longitude);
        return weatherService.getCurrentWeather(latitude, longitude);
    }

//    @SneakyThrows
//    @GetMapping("weatherapi/{latitude}/{longitude}")
//    public CurrentWeatherDto getCurrentWeatherByWeatherApi(@PathVariable Double latitude,@PathVariable Double longitude) {
//        log.debug("Request received. Find current weather data by weather api. latitude : {},longitude : {}", latitude,longitude);
//        return weatherId;
//    }

    @GetMapping("openmeteo/forecast/{forecastDays}/{latitude}/{longitude}")
    public BatchWeatherDto getForecastWeatherByOpenMeteo(@PathVariable Long forecastDays, @PathVariable Double latitude, @PathVariable Double longitude) {
        log.debug("Request received. Find forecast days in specific place by Open Meteo, forecastDays : {} , latitude : {},longitude : {}", forecastDays, latitude, longitude);
        return weatherService.getForecastWeather(forecastDays, latitude, longitude);
    }

//    @GetMapping("weatherapi/forecast/{forecastDays}/{latitude}/{longitude}")
//    public ForecastWeatherDto getCurrentWeatherByWeatherApi(@PathVariable Long forecastDays,@PathVariable Double latitude,@PathVariable Double longitude) {
//        log.debug("Request received. Find forecast days in specific place by WeatherApi, forecastDays : {} , latitude : {},longitude : {}", forecastDays,latitude,longitude);
//        return weatherId;
//    }

    @GetMapping("openmeteo/historical/start-{startDate}/end-{endDate}/{latitude}/{longitude}")
    public BatchWeatherDto getCurrentWeatherByWeatherApi(@PathVariable @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @PathVariable @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @PathVariable Double latitude, @PathVariable Double longitude) {
        log.debug("Request received. Find historical data in specific place by open meteo, date range : {} - {} , latitude : {},longitude : {}", startDate, endDate, latitude, longitude);
        return weatherService.getHistoricalWeather(startDate, endDate, latitude, longitude);
    }

}

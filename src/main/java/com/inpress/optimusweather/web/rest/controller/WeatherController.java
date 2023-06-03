package com.inpress.optimusweather.web.rest.controller;


import com.inpress.optimusweather.service.dto.WeatherDto;
import com.inpress.optimusweather.service.dto.ForecastWeatherDto;
import com.inpress.optimusweather.service.dto.HistoricalWeatherDto;
import com.inpress.optimusweather.service.dto.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

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
        log.debug("Request received. Find current weather data by open meteo. latitude : {},longitude : {}", latitude,longitude);
        return weatherService.getCurrentWeather(latitude,latitude);
    }

//    @SneakyThrows
//    @GetMapping("weatherapi/{latitude}/{longitude}")
//    public CurrentWeatherDto getCurrentWeatherByWeatherApi(@PathVariable Double latitude,@PathVariable Double longitude) {
//        log.debug("Request received. Find current weather data by weather api. latitude : {},longitude : {}", latitude,longitude);
//        return weatherId;
//    }

    @GetMapping("openmeteo/forecast/{forecastDays}/{latitude}/{longitude}")
    public ForecastWeatherDto getForecastWeatherByOpenMeteo(@PathVariable Long forecastDays, @PathVariable Double latitude, @PathVariable Double longitude) {
        log.debug("Request received. Find forecast days in specific place by Open Meteo, forecastDays : {} , latitude : {},longitude : {}", forecastDays,latitude,longitude);
        return null;
    }

//    @GetMapping("weatherapi/forecast/{forecastDays}/{latitude}/{longitude}")
//    public ForecastWeatherDto getCurrentWeatherByWeatherApi(@PathVariable Long forecastDays,@PathVariable Double latitude,@PathVariable Double longitude) {
//        log.debug("Request received. Find forecast days in specific place by WeatherApi, forecastDays : {} , latitude : {},longitude : {}", forecastDays,latitude,longitude);
//        return weatherId;
//    }

    @GetMapping("openmeteo/historical/start-{startDate}/end-{endDate}/{latitude}/{longitude}")
    public HistoricalWeatherDto getCurrentWeatherByWeatherApi(@PathVariable @NotNull Long startDate, @PathVariable Long endDate, @PathVariable Double latitude, @PathVariable Double longitude) {
        log.debug("Request received. Find historical data in specific place by open meteo, date range : {} - {} , latitude : {},longitude : {}", startDate,endDate,latitude,longitude);
        return null;
    }

}

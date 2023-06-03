package com.inpress.optimusweather.web.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inpress.optimusweather.service.dto.WeatherDto;
import com.inpress.optimusweather.service.dto.ForecastWeatherDto;
import com.inpress.optimusweather.service.dto.HistoricalWeatherDto;
import com.inpress.optimusweather.web.rest.error.OptimusWeatherException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Slf4j
@RequiredArgsConstructor
public abstract class OptimusWeatherAPI {

    private final RestTemplate restTemplate;
    protected final ObjectMapper objectMapper;

    /**
     * show the current weather in specific area
     * @param longitude longitude
     * @param latitude latitude
     * @return the {@link WeatherDto}
     */
    abstract WeatherDto currentWeather(Double latitude, Double longitude);

    /**
     * show daily forecast in specific area
     * @param forecastDays num of days to be forecasted
     * @param longitude longitude
     * @param latitude latitude
     * @return the data of weather {@link ForecastWeatherDto} in specific area
     */
    abstract ForecastWeatherDto forecastWeather(Long forecastDays,Double latitude,Double longitude);

    abstract HistoricalWeatherDto historicalWeather(Long startDate,Long endDate,Double latitude,Double longitude);

    protected final String restGet(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restExchange(url, GET, new HttpEntity<>(headers));
    }

    protected final String restPost(String url, String jsonData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restExchange(url, POST, new HttpEntity<>(jsonData, headers));
    }

    protected final String restPut(String url, String jsonData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restExchange(url, PUT, new HttpEntity<>(jsonData, headers));
    }

    protected final String restDelete(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restExchange(url, DELETE, new HttpEntity<>(headers));
    }

    private final String restExchange(String url, HttpMethod httpMethod, HttpEntity<String> httpEntity) {
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);

            if (responseEntity.getStatusCodeValue() < 200 || responseEntity.getStatusCodeValue() >= 300) {
                log.error("error received :: {} , {} , {} , {} , {}",
                        responseEntity.getBody(), responseEntity.getStatusCode(), httpMethod, url, httpEntity);
                throw new OptimusWeatherException(responseEntity.getStatusCode().value(),responseEntity.getBody());
            }
            return responseEntity.getBody();
        } catch (RestClientResponseException exception) {
            log.error("error received :: {} , {} , {} , {} , {}",
                    exception.getResponseBodyAsString(), exception.getRawStatusCode(), httpMethod, url, httpEntity);
            throw new OptimusWeatherException(exception.getRawStatusCode(),exception.getResponseBodyAsString());
        }
    }
}

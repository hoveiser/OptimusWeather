package com.inpress.optimusweather.service.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@Testcontainers
class WeatherServiceTest {

    /**
     * need test for api using mockMvc to handle data is the same style as before
     */
    @Test
    void getCurrentWeather() {
    }

    /**
     * need test for api using mockMvc to handle data is the same style as before
     */
    @Test
    void getForecastWeather() {
    }

    /**
     * need test for api using mockMvc to handle data is the same style as before and also need to check unique constraint by test container
     * or in memory db and also check some functionality for get part of date from db and get others from api
     */
    @Test
    void getHistoricalWeather() {
    }
}
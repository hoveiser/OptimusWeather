package com.inpress.optimusweather.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@RequiredArgsConstructor
public enum WeatherCode {

    CLEAR_SKY(0),
    MAINLY_CLEAR(1),
    PARTLY_CLOUDY(2),
    OVERCAST(3),
    Fog(45),
    DEPOSITING_RIME_FOG(48),
    DRIZZLE_LIGHT(51),
    MODERATE(53),
    DENSE_INTENSITY(55),
    RAIN_SLIGHT_INTENSITY(61),
    RAIN_MODERATE_INTENSITY(63),
    RAIN_HEAVY_INTENSITY(65),
    FREEZING_RAIN_LIGHT(66),
    FREEZING_RAIN_HEAVY (67),
    SNOW_FALL_SLIGHT(71),
    SNOW_FALL_MODERATE(73),
    SNOW_FALL_HEAVY(75),
    RAIN_SHOWER_SLIGHT(80),
    RAIN_SHOWER_MODERATE(81),
    RAIN_SHOWER_HEAVY(82),
    SNOW_SHOWER_SLIGHT(85),
    SNOW_SHOWER_HEAVY(86),
    THUNDERSTORM(95),
    THUNDERSTORM_SLIGHT_HAIL(96),
    THUNDERSTORM_HEAVY_HAIL(99);

    public final int code;

    private static final Map<Integer, WeatherCode> weatherCodeMap = new HashMap<>();

    static {
        for (WeatherCode weatherCode : WeatherCode.values()) {
            weatherCodeMap.put(
                    weatherCode.getCode(),
                    weatherCode

            );
        }
    }

    public static WeatherCode castWeatherCode(int code) {
        return weatherCodeMap.get(code);
    }

    public static List<WeatherCode> convertToWeather(List<Integer> weatherCodeNumbers) {
        List<WeatherCode> weatherCodes = new ArrayList<>();
        for (Integer weatherCodeNumber : weatherCodeNumbers) {
            weatherCodes.add(castWeatherCode(weatherCodeNumber));
        }
        return weatherCodes;
    }

}

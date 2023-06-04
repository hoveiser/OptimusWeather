package com.inpress.optimusweather.service.dto;

import com.inpress.optimusweather.entity.Weather;
import com.inpress.optimusweather.repository.WeatherRepository;
import com.inpress.optimusweather.web.remote.OpenMeteoClient;
import com.inpress.optimusweather.web.rest.error.OptimusWeatherError;
import com.inpress.optimusweather.web.rest.error.OptimusWeatherException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final OpenMeteoClient openMeteoClient;

    private final WeatherRepository weatherRepository;

    private final Integer forecastDaysLimit = 10;
    private final Integer historicalDaysLimit = 30;

    public WeatherDto getCurrentWeather(Double latitude, Double longitude) {
        WeatherDto weatherDto = openMeteoClient.currentWeather(latitude, longitude);
        return weatherDto;
    }

    public BatchWeatherDto getForecastWeather(Long forecastDays, Double latitude, Double longitude) {
        if (forecastDays > forecastDaysLimit) {
            throw new OptimusWeatherException(OptimusWeatherError.BAD_REQUEST_FORECAST_LIMIT, "forecast days are much more than the limit", forecastDaysLimit);
        }
        BatchWeatherDto forecastBatchWeatherDto = openMeteoClient.forecastWeather(forecastDays, latitude, longitude);
        return forecastBatchWeatherDto;
    }

    public BatchWeatherDto getHistoricalWeather(Date startDate, Date endDate, Double latitude, Double longitude) {
        long periodDays = Duration.between(startDate.toInstant(), endDate.toInstant()).toDays();
        if (periodDays > historicalDaysLimit || periodDays <= 0) {
            throw new OptimusWeatherException(OptimusWeatherError.BAD_REQUEST_HISTORICAL_LIMIT,
                    "historical days are much more than the limit or equal or less than zero!", historicalDaysLimit);
        }
        List<Weather> availableHistoricalData = weatherRepository.findByLatitudeAndLongitudeAndTimeBetweenOrderByTimeAsc(latitude, longitude, startDate, endDate);
        if (periodDays > availableHistoricalData.size()) {
            Pair<Date, Date> missedDaysIntervals = findMissedDaysIntervals(startDate, endDate, availableHistoricalData);
            BatchWeatherDto historicalBatchWeatherDto = openMeteoClient.historicalWeather(missedDaysIntervals.getFirst(), missedDaysIntervals.getSecond(), latitude, longitude);
            weatherRepository.saveAllAndFlush(fromDtos(historicalBatchWeatherDto));
            historicalBatchWeatherDto.getWeatherDtos().addAll(toDtos(availableHistoricalData));
            return historicalBatchWeatherDto;
        } else {
            return new BatchWeatherDto(latitude, longitude, toDtos(availableHistoricalData));
        }
    }


    private Pair<Date, Date> findMissedDaysIntervals(Date startDate, Date endDate, List<Weather> weathersFindsInDB) {
        Date startMissedDate = null;
        Date endMissedDate = null;
        Date current = startDate;
        while (current.before(endDate) || current.equals(endDate)) {
            Date finalCurrent = current;
            boolean findDay = weathersFindsInDB.stream().anyMatch(weather -> weather.getTime().equals(finalCurrent));
            if (!findDay) {
                if (startMissedDate == null || startMissedDate.after(finalCurrent)) {
                    startMissedDate = finalCurrent;
                } else if (endMissedDate == null || endMissedDate.before(finalCurrent)) {
                    endMissedDate = finalCurrent;
                }
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.DATE, 1);
            current = calendar.getTime();
        }
        return Pair.of(startMissedDate, endMissedDate);
    }


    private Set<Weather> fromDtos(BatchWeatherDto batchWeatherDto) {
        Set<Weather> weathers = new HashSet<>();
        for (WeatherDto weatherDto : batchWeatherDto.getWeatherDtos()) {
            weathers.add(fromDto(weatherDto));
        }
        return weathers;
    }

    private Weather fromDto(WeatherDto weatherDto, Weather weather) {
        weather.setTime(weatherDto.getTime());
        weather.setLatitude(weatherDto.getLatitude());
        weather.setLongitude(weatherDto.getLongitude());
        weather.setWeatherCode(weatherDto.getWeatherCode());
        weather.setWindDirection(weatherDto.getWindDirection());
        weather.setWindSpeed(weatherDto.getWindSpeed());
        weather.setTimeZone(weatherDto.getTimeZone());
        weather.setTemperature(weatherDto.getTemperature());
        weather.setGeoData(weather.getLatitude()+","+weather.getLongitude());
        return weather;
    }

    private SortedSet<WeatherDto> toDtos(List<Weather> weatherList) {
        return weatherList.stream().map(this::toDto).collect(Collectors.toCollection(TreeSet::new));
    }

    private WeatherDto toDto(Weather weather) {
        return WeatherDto.builder().time(weather.getTime()).timeZone(weather.getTimeZone())
                .temperature(weather.getTemperature()).windSpeed(weather.getWindSpeed()).windDirection(weather.getWindDirection())
                .latitude(weather.getLatitude()).longitude(weather.getLongitude()).weatherCode(weather.getWeatherCode()).build();
    }

    private Weather fromDto(WeatherDto weatherDto) {
        Weather weather = new Weather();
        fromDto(weatherDto, weather);
        return weather;
    }


}

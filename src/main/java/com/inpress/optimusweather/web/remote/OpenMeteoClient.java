package com.inpress.optimusweather.web.remote;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inpress.optimusweather.service.dto.WeatherCode;
import com.inpress.optimusweather.service.dto.WeatherDto;
import com.inpress.optimusweather.service.dto.BatchWeatherDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OpenMeteoClient extends OptimusWeatherAPI {

    @Value("${wsn.openmeteo}")
    private String openMeteoUrl;

    @Value("${wsn.openmeteo.archive}")
    private String openMeteoArchiveUrl;


    private static final String CURRENT_WEATHER_URL = "forecast?latitude={latitude}&longitude={longitude}&current_weather=true";
    private static final String FORECAST_WEATHER_URL = "forecast?latitude={latitude}&longitude={longitude}&forecast_days={forecastDay}&&daily=temperature_2m_max,weathercode,windspeed_10m_max,winddirection_10m_dominant&timezone=GMT";
    private static final String HISTORICAL_WEATHER_URL = "era5?latitude={latitude}&longitude={longitude}&start_date={startDate}&end_date={endDate}&daily=temperature_2m_max,weathercode,windspeed_10m_max,winddirection_10m_dominant&timezone=GMT";

    public OpenMeteoClient(RestTemplate restTemplate, ObjectMapper objectMapper, DecimalFormat decimalFormat) {
        super(restTemplate, objectMapper, decimalFormat);
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
        currentWeather.setWeatherCode(WeatherCode.castWeatherCode(jsonNode.get("current_weather").get("weathercode").asInt()));

        currentWeather.setLongitude(BigDecimal.valueOf(jsonNode.get("longitude").asDouble()).setScale(2, RoundingMode.UP).doubleValue());
        currentWeather.setLatitude(BigDecimal.valueOf(jsonNode.get("latitude").asDouble()).setScale(2, RoundingMode.UP).doubleValue());
        currentWeather.setTimeZone(jsonNode.get("timezone").asText());
        return currentWeather;
    }

    @SneakyThrows
    @Override
    public BatchWeatherDto forecastWeather(Long forecastDays, Double latitude, Double longitude) {
        Map<String, String> params = new HashMap<>();
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("forecastDay", String.valueOf(forecastDays));
        String forecastResponse = restGet(
                UriComponentsBuilder.
                        fromHttpUrl(openMeteoUrl + FORECAST_WEATHER_URL).
                        buildAndExpand(params).
                        toUriString()
        );

        return toWeatherDtoList(forecastResponse);
    }

    @SneakyThrows
    @Override
    public BatchWeatherDto historicalWeather(Date startDate, Date endDate, Double latitude, Double longitude) {
        Map<String, String> params = new HashMap<>();
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("startDate", dateFormat.format(startDate));
        params.put("endDate", dateFormat.format(endDate));
        String historicalResponse = restGet(
                UriComponentsBuilder.
                        fromHttpUrl(openMeteoArchiveUrl + HISTORICAL_WEATHER_URL).
                        buildAndExpand(params).
                        toUriString()
        );

        return toWeatherDtoList(historicalResponse);
    }

    private BatchWeatherDto toWeatherDtoList(String batchJsonData) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(batchJsonData);
        double latitude = BigDecimal.valueOf(jsonNode.get("latitude").asDouble()).setScale(2, RoundingMode.UP).doubleValue();
        double longitude = BigDecimal.valueOf(jsonNode.get("longitude").asDouble()).setScale(2, RoundingMode.UP).doubleValue();
        String timeZone = jsonNode.get("timezone").asText();
        BatchWeatherDto batchWeatherDto = new BatchWeatherDto();
        batchWeatherDto.setLatitude(latitude);
        batchWeatherDto.setLongitude(longitude);

        JsonNode dailyBatchData = jsonNode.get("daily");
        List<Date> batchTimes = objectMapper.readValue(dailyBatchData.get("time").toString(), new TypeReference<>() {
        });
        List<Double> batchTemperature = objectMapper.readValue(dailyBatchData.get("temperature_2m_max").toString(), new TypeReference<>() {
        });
        List<WeatherCode> batchWeatherCode = WeatherCode.convertToWeather(objectMapper.readValue(dailyBatchData.get("weathercode").toString(), new TypeReference<>() {
        }));
        List<Double> batchWindSpeed = objectMapper.readValue(dailyBatchData.get("windspeed_10m_max").toString(), new TypeReference<>() {
        });
        List<Integer> batchWindDirection = objectMapper.readValue(dailyBatchData.get("winddirection_10m_dominant").toString(), new TypeReference<>() {
        });

        for (int i = 0; i < batchTimes.size(); i++) {
            WeatherDto weatherDto = new WeatherDto(latitude, longitude, timeZone, batchTemperature.get(i), batchWindSpeed.get(i), batchWindDirection.get(i), batchWeatherCode.get(i), batchTimes.get(i));
            batchWeatherDto.getWeatherDtos().add(weatherDto);
        }

        return batchWeatherDto;
    }
}

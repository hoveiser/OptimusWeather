package com.inpress.optimusweather.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto implements Comparable<WeatherDto>{

    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("timezone")
    private String timeZone;
    @JsonProperty("temperature")
    private double temperature;
    @JsonProperty("windspeed")
    private double windSpeed;
    @JsonProperty("winddirection")
    private int windDirection;
    @JsonProperty("weathercode")
    private WeatherCode weatherCode;
    @JsonProperty("time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date time;

    @Override
    public int compareTo(WeatherDto o) {
        return time.compareTo(o.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherDto that = (WeatherDto) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.temperature, temperature) != 0) return false;
        if (Double.compare(that.windSpeed, windSpeed) != 0) return false;
        if (windDirection != that.windDirection) return false;
        if (!Objects.equals(timeZone, that.timeZone)) return false;
        if (weatherCode != that.weatherCode) return false;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + time.hashCode();
        return result;
    }
}

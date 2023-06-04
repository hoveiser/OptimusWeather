package com.inpress.optimusweather.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inpress.optimusweather.service.dto.WeatherCode;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather",uniqueConstraints = {
        @UniqueConstraint(name = "baseUnique",columnNames = {"geoData","time"})
})
public class Weather extends BaseEntity implements Serializable,Comparable<Weather> {

    @NotNull
    private String geoData;
    private Double latitude;
    private Double longitude;
    private String timeZone;
    private Double temperature;
    private Double windSpeed;
    private Integer windDirection;
    private WeatherCode weatherCode;

    @Temporal(TemporalType.DATE)
    private Date time;

    @Override
    public int compareTo(Weather o) {
        return time.compareTo(o.getTime());
    }
}

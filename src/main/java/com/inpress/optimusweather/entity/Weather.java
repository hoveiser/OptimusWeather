package com.inpress.optimusweather.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inpress.optimusweather.service.dto.WeatherCode;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
        @UniqueConstraint(name = "baseUnique",columnNames = {"latitude","time"})
})
public class Weather extends BaseEntity implements Serializable,Comparable<Weather> {
    private Double latitude;
    private Double longitude;
    private String timeZone;
    private Double temperature;
    private Double windSpeed;
    private Integer windDirection;
    private WeatherCode weatherCode;
    private Date time;

    @Override
    public int compareTo(Weather o) {
        return time.compareTo(o.getTime());
    }
}

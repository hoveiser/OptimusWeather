package com.inpress.optimusweather.repository;

import com.inpress.optimusweather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findByLatitudeAndLongitudeAndTime(Double latitude, Double longitude, Date time);

    List<Weather> findByLatitudeAndLongitudeAndTimeBetweenOrderByTimeAsc(Double latitude, Double longitude, Date timeStart, Date timeEnd);
}

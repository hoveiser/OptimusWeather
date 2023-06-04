package com.inpress.optimusweather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.boot.model.source.spi.IdentifierSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Clock;
import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
public class OptimusWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptimusWeatherApplication.class, args);
    }

    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("Europe/London"));
    }

    @Bean
    public DecimalFormat decimalFormat(){
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.UP);
        return decimalFormat;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);

        return mapper;
    }

}

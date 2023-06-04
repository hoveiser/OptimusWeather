package com.inpress.optimusweather.web.rest.error;

import org.springframework.http.HttpStatus;

public enum OptimusWeatherError {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Bad Request"),
    BAD_REQUEST_UPDATE_STATUS(HttpStatus.BAD_REQUEST.value(), "Bad Request message:{0},desiredStatus :{1}" ),
    BAD_REQUEST_FORECAST_LIMIT(HttpStatus.BAD_REQUEST.value(), "Bad Request message: {0}, the limitation for forecast days is : {1}"),
    BAD_REQUEST_HISTORICAL_LIMIT(HttpStatus.BAD_REQUEST.value(), "Bad Request message: {0}, the limitation intervals for Historical is : {1} days "),
    EXTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An external server cannot process the request successfully."),
    CAN_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE.value(), "{0} {1} cannot be found"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "You do not have permission to access this resource.");

    private final int    code;
    private final String message;

    OptimusWeatherError(int code, String message) {
        this.code    = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

package com.inpress.optimusweather.web.rest.error;

import org.springframework.http.HttpStatus;

public enum OptimusWeatherError {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "Bad Request"),
    BAD_REQUEST_UPDATE_STATUS(HttpStatus.BAD_REQUEST.value(), "Bad Request message:{0},desiredStatus :{1}" ),
    BAD_REQUEST_SUBMIT_VISIT(HttpStatus.BAD_REQUEST.value(), "Bad Request message: {0},visit Id: {1}"),
    EXTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An external server cannot process the request successfully."),
    CAN_NOT_FOUND(HttpStatus.NOT_ACCEPTABLE.value(), "{0} {1} cannot be found"),

    CAN_NOT_VALIDATE_JWT_TOKEN(HttpStatus.FORBIDDEN.value(), "Can not validate JWT token. Please send valid JWT token."),
    JWT_TOKEN_CAN_NOT_BE_NULL(HttpStatus.UNAUTHORIZED.value(), "JWT authentication token can not be null. Please send valid JWT token."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "You do not have permission to access this resource."),
    // parser errors
    API_RETURNED_NULL(HttpStatus.NOT_ACCEPTABLE.value(), "API {0} returned DATA == NULL for {1} {2}"),
    REPORT_NOT_GENERATE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Report not generated yet."),

    EMIT_WEBSOCKET_EVENT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error during emit a WebSocket event."),
    UNPROCESSABLE_ENDPOINT(HttpStatus.UNPROCESSABLE_ENTITY.value(), "{0} is not an supported endpoint"),
    BASIC_MRANO_VISITS_OUT_OF_RANGE(HttpStatus.NOT_ACCEPTABLE.value(),
            "There are currently two visits for the basic-mrano reading; You can not add a new one");

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

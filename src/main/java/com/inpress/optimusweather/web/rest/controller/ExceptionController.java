package com.inpress.optimusweather.web.rest.controller;

import com.inpress.optimusweather.service.dto.BasicResponse;
import com.inpress.optimusweather.web.rest.error.OptimusWeatherException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(OptimusWeatherException.class)
    public ResponseEntity<BasicResponse<?>> handleMsReadingExceptions(OptimusWeatherException ex) {
        String message = ex.getErrorMessage();
        int errorCode = ex.getErrorCode();
        log.error(message, ex);
        return new ResponseEntity<>(BasicResponse.dataObject(HttpStatus.valueOf(errorCode), message), HttpStatus.valueOf(errorCode));
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<BasicResponse<?>> handleExceptions(Throwable ex, WebRequest request) {
        String extMessage = ex.getClass().getSimpleName() + " -> " + request.getDescription(true);
        String message = ExceptionUtils.getRootCauseMessage(ex);
        extMessage = extMessage + " : " + message + " {}";
        log.error(extMessage, ex);
        return new ResponseEntity<>(BasicResponse.internalServerError(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.inpress.optimusweather.service.dto;

import org.springframework.http.HttpStatus;

public class BasicResponse<T> {
    private int responseCode;
    private String responseMessage;
    private T data;

    public BasicResponse(int responseCode) {
        this.responseCode = responseCode;
    }

    public BasicResponse(int responseCode, T data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    public BasicResponse(int responseCode, T data, String message) {
        this.responseCode = responseCode;
        this.data = data;
        this.responseMessage = message;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static BasicResponse<?> internalServerError(String message) {
        return dataObject(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static BasicResponse<?> unauthorizedError() {
        return dataObject(HttpStatus.UNAUTHORIZED, null);
    }

    public static <T> BasicResponse<T> dataObject(T dataObject) {
        return dataObject(HttpStatus.OK, dataObject);
    }

    public static <T> BasicResponse<T> dataObject(HttpStatus statusCode, T dataObject) {
        return new BasicResponse<T>(statusCode.value(), dataObject);
    }
}

package com.inpress.optimusweather.web.rest.error;

public class OptimusWeatherException extends RuntimeException{
    private static final long serialVersionUID = -3257022882385328074L;
    private final int errorCode;
    private String errorMessage;

    public OptimusWeatherException(int status, String msg) {
        super(msg);
        this.errorCode = status;
        this.errorMessage = msg;
    }

    public OptimusWeatherException(OptimusWeatherError error) {
        super(error.getMessage());
        this.errorCode = error.getCode();
        this.errorMessage = error.getMessage();
    }



    public OptimusWeatherException(OptimusWeatherError error, Object... params) {
        this(error);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                errorMessage = errorMessage.replace("{" + i + "}", String.valueOf(params[i]));
            }
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "MsreadingmranoException [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
    }

    public static OptimusWeatherException wrap(Throwable cause) {
        OptimusWeatherException result;
        if (cause instanceof OptimusWeatherException) {
            result = (OptimusWeatherException) cause;
        } else {
            // TODO detailed wrap
            result = new OptimusWeatherException(OptimusWeatherError.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}

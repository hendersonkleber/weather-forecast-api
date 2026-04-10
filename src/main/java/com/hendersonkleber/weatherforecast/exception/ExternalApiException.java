package com.hendersonkleber.weatherforecast.exception;

public class ExternalApiException extends RuntimeException {
    private final String service;

    public ExternalApiException(String service, String message) {
        super(message);
        this.service = service;
    }

    public ExternalApiException(String service, String message, Throwable cause) {
        super(message, cause);
        this.service = service;
    }
}

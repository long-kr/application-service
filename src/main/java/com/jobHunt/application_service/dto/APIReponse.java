package com.job_hunt.application_service.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIReponse<T> {
    private Boolean success;
    private String message;
    private T data;
    private Object error;
    private Instant timestamp;

    public static <T> APIReponse<T> success(String message, final T data) {
        return new APIReponse<>(true, message, data, null, Instant.now());
    }

    public static <T> APIReponse<T> failure(String message, Object error) {
        return new APIReponse<>(false, message, null, error, Instant.now());
    }

}

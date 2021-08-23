package com.rms.review_and_mileage_service.tools.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

    private final String message;

    private final String content;

}
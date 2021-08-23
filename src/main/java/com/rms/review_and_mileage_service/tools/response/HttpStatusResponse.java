package com.rms.review_and_mileage_service.tools.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpStatusResponse {

    public static final ResponseEntity<Void> BAD_REQUEST = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

}

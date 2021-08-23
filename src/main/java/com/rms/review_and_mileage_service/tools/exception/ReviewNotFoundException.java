package com.rms.review_and_mileage_service.tools.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException() {
        super("review is not exist");
    }

}

package com.rms.review_and_mileage_service.tools.exception;

public class ReviewDuplicateException extends RuntimeException {

    public ReviewDuplicateException() {
        super("already posted review on this place before");
    }
}

package com.rms.review_and_mileage_service.tools.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("user account is not exist");
    }

}

package com.rms.review_and_mileage_service.tools.exception;

public class InvalidEventActionException extends RuntimeException {

    public InvalidEventActionException(String action) {
        super("[ " + action + " ] is invalid event action");
    }
}

package com.rms.review_and_mileage_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventRequest {

    @NotNull
    private final String type;

    @NotNull
    private final String action;

    @NotNull
    private final String reviewId;

    @NotNull
    private final String content;

    @NotNull
    private final List<String> attachedPhotoIds;

    @NotNull
    private final String userId;

    @NotNull
    private final String placeId;

}

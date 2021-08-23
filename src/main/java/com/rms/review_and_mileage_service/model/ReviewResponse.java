package com.rms.review_and_mileage_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewResponse {

    private final int review_id;

    private final int place_id;

    private final int user_id;

    private final String content;

    private final String create_time;

    private final String update_time;

}

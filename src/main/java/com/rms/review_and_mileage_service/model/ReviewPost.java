package com.rms.review_and_mileage_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewPost {

    private final int place_id;

    private final int user_id;

    private final String content;

    private final List<String> images;

}

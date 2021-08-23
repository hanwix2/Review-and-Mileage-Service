package com.rms.review_and_mileage_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MileageRecord {

    private final int id;

    private final int review_id;

    private final String content;

    private final int amount;

    private final String time;

}

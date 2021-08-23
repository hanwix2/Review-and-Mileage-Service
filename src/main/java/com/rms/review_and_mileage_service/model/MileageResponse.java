package com.rms.review_and_mileage_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MileageResponse {

    private final int user_id;

    private final List<MileageRecord> mileageRecordings;

    private final int totalMileage;

}

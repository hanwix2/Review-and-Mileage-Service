package com.rms.review_and_mileage_service.controller;

import com.rms.review_and_mileage_service.model.MileageResponse;
import com.rms.review_and_mileage_service.service.MileageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MileageController {

    private final MileageService mileageService;

    @GetMapping("/mileages")
    public MileageResponse getMileages(@RequestParam int user_id) {
        return mileageService.getMileages(user_id);
    }

}

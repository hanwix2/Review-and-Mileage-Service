package com.rms.review_and_mileage_service.controller;

import com.rms.review_and_mileage_service.model.EventRequest;
import com.rms.review_and_mileage_service.model.ReviewResponse;
import com.rms.review_and_mileage_service.service.ReviewService;
import com.rms.review_and_mileage_service.tools.constant.EventsAction;
import com.rms.review_and_mileage_service.tools.exception.InvalidEventActionException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/events")
    public void events(@RequestBody @Valid EventRequest eventRequest) {

        String action = eventRequest.getAction();

        switch (action) {
            case EventsAction.ADD:
                reviewService.postReview(eventRequest);
                break;
            case EventsAction.MOD:
                reviewService.editReview(eventRequest);
                break;
            case EventsAction.DELETE:
                reviewService.deleteReview(eventRequest);
                break;
            default:
                throw new InvalidEventActionException(action);
        }

    }

    @GetMapping("/reviews")
    public List<ReviewResponse> getReviews(@RequestParam(required = false) Integer place_id) {
        return reviewService.getReviews(place_id);
    }

}

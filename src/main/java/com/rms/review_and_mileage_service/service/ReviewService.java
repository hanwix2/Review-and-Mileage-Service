package com.rms.review_and_mileage_service.service;

import com.rms.review_and_mileage_service.mapper.ReviewMapper;
import com.rms.review_and_mileage_service.model.*;
import com.rms.review_and_mileage_service.tools.exception.ReviewDuplicateException;
import com.rms.review_and_mileage_service.tools.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final MileageService mileageService;

    private final UserService userService;

    private final ReviewMapper reviewMapper;

    @Transactional
    public void postReview(EventRequest eventRequest) {

        userService.checkUserExists(Integer.parseInt(eventRequest.getUserId()));

        ReviewPost review = getReviewPostFromEvntReq(eventRequest);

        boolean isFirstReview = !reviewMapper.isReviewExists(review.getPlace_id());

        if (!isFirstReview) {
            checkDuplicationReview(review.getPlace_id(), review.getUser_id());
        }

        int review_id = addReview(review);

        mileageService.earnMileageInReview(review, review_id, isFirstReview);

    }

    private int addReview(ReviewPost review) {
        int review_id = reviewMapper.addReview(review);

        if (review.getImages().size() > 0) {

            List<ReviewImage> reviewImages = new ArrayList<>();

            for (String image : review.getImages()) {
                reviewImages.add(new ReviewImage(review_id, image));
            }

            reviewMapper.addReviewImages(reviewImages);
        }

        return review_id;
    }

    private void checkDuplicationReview(int place_id, int user_id) {

        if (reviewMapper.isUserReviewExists(place_id, user_id)) {
            throw new ReviewDuplicateException();
        }

    }

    private ReviewPost getReviewPostFromEvntReq(EventRequest eventRequest) {
        return new ReviewPost(
                Integer.parseInt(eventRequest.getPlaceId()),
                Integer.parseInt(eventRequest.getUserId()),
                eventRequest.getContent(),
                eventRequest.getAttachedPhotoIds());
    }

    public List<ReviewResponse> getReviews(Integer place_id) {
        return reviewMapper.getReviews(place_id);
    }

    @Transactional
    public void editReview(EventRequest eventRequest) {

        int user_id = Integer.parseInt(eventRequest.getUserId());

        userService.checkUserExists(user_id);

        ReviewResponse review = reviewMapper.getUserReview(Integer.parseInt(eventRequest.getPlaceId()), user_id);

        if (review == null) {
            throw new ReviewNotFoundException();
        }

        List<ReviewImageResponse> images = reviewMapper.getReviewImages(review.getReview_id());

        List<String> modImages = eventRequest.getAttachedPhotoIds();

        mileageService.calcMileageInReviewImageMod(images.size(), modImages.size(), review, user_id);

        reviewMapper.editReview(review.getReview_id(), eventRequest.getContent());

        List<Integer> delImages = new ArrayList<>();

//        for (ReviewImageResponse img : images) {
//            for ()
//        }
//
////        ReviewPost review = getReviewPostFromEvntReq(eventRequest);



    }

    public void deleteReview(EventRequest eventRequest) {
        int user_id = Integer.parseInt(eventRequest.getUserId());

        userService.checkUserExists(user_id);

        ReviewResponse review = reviewMapper.getUserReview(Integer.parseInt(eventRequest.getReviewId()), user_id);

        if (review == null) {
            throw new ReviewNotFoundException();
        }

        int review_id = review.getReview_id();

        reviewMapper.deleteReview(review_id);

        mileageService.retrieveMileageOnDeletion(review_id, user_id);

    }

}

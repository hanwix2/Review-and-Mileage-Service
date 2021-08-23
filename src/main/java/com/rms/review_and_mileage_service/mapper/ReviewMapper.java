package com.rms.review_and_mileage_service.mapper;

import com.rms.review_and_mileage_service.model.ReviewImage;
import com.rms.review_and_mileage_service.model.ReviewImageResponse;
import com.rms.review_and_mileage_service.model.ReviewPost;
import com.rms.review_and_mileage_service.model.ReviewResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    int addReview(ReviewPost review);

    List<ReviewResponse> getReviews(Integer place_id);

    boolean isUserReviewExists(int place_id, int user_id);

    boolean isReviewExists(int place_id);

    void addReviewImages(List<ReviewImage> reviewImages);

    ReviewResponse getUserReview(int review_id, int user_id);

    List<ReviewImageResponse> getReviewImages(int review_id);

    void editReview(int review_id, String content);

    void deleteReview(int review_id);

}

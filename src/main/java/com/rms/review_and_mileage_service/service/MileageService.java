package com.rms.review_and_mileage_service.service;

import com.rms.review_and_mileage_service.mapper.MileageMapper;
import com.rms.review_and_mileage_service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MileageService {

    private final MileageMapper mileageMapper;

    private final UserService userService;

    public void earnMileageInReview(ReviewPost review, int review_id, boolean isFirstReview) {

        List<MileagePost> mileagePosts = new ArrayList<>();

        if (review.getContent().length() > 0) {
            mileagePosts.add(new MileagePost(review.getUser_id(), review_id, "리뷰 내용 작성", 1));
        }

        if (review.getImages().size() > 0) {
            mileagePosts.add(new MileagePost(review.getUser_id(), review_id, "리뷰 사진 추가", 1));
        }

        if (isFirstReview) {
            mileagePosts.add(new MileagePost(review.getUser_id(), review_id, "첫번째 리뷰 작성", 1));
        }

        recordMileageLogs(mileagePosts);

    }

    private void recordMileageLogs(List<MileagePost> mileageRecords) {
        mileageMapper.recordMileageLogs(mileageRecords);
    }

    private void recordMileage(MileagePost mileagePost) {
        mileageMapper.recordMileage(mileagePost);
    }

    @Transactional
    public MileageResponse getMileages(int user_id) {
        userService.checkUserExists(user_id);

        List<MileageRecord> recordList = mileageMapper.getMileages(user_id);

        int totalMileage = 0;

        for (MileageRecord record : recordList) {
            totalMileage += record.getAmount();
        }

        return new MileageResponse(user_id, recordList, totalMileage);
    }

    public void calcMileageInReviewImageMod(int beforeImgSize, int afterImgSize, ReviewResponse review, int user_id) {
        if (beforeImgSize == 0 && afterImgSize > 0) {
            recordMileage(new MileagePost(user_id, review.getReview_id(), "리뷰 사진 추가", 1));
        } else if (beforeImgSize > 0 && afterImgSize == 0) {
            recordMileage(new MileagePost(user_id, review.getReview_id(), "리뷰 사진 삭제", -1));
        }
    }

    public void retrieveMileageOnDeletion(int review_id, int user_id) {
        int mileageRetrieve = mileageMapper.getMileagesSumByReview(review_id, user_id);

        recordMileage(new MileagePost(user_id, review_id, "리뷰 삭제", -mileageRetrieve));
    }
}

package com.rms.review_and_mileage_service.mapper;

import com.rms.review_and_mileage_service.model.MileagePost;
import com.rms.review_and_mileage_service.model.MileageRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MileageMapper {

    void recordMileageLogs(List<MileagePost> mileagePosts);

    void recordMileage(MileagePost mileagePost);

    List<MileageRecord> getMileages(int user_id);

    int getMileagesSumByReview(int review_id, int user_id);

}

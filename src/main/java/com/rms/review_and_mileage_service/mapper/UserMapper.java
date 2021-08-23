package com.rms.review_and_mileage_service.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean isUserExists(int user_id);

}

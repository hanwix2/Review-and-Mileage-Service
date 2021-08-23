package com.rms.review_and_mileage_service.service;

import com.rms.review_and_mileage_service.mapper.UserMapper;
import com.rms.review_and_mileage_service.tools.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void checkUserExists(int user_id) {
        if(!userMapper.isUserExists(user_id)) {
            throw new UserNotFoundException();
        }
    }

}

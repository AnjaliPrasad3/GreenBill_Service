package com.impat.green_bill.service.impl;

import com.impat.green_bill.service.UserActivityService;
import com.impat.green_bill.entities.UserActivity;
import com.impat.green_bill.repository.UserActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class UserActivityServiceImpl implements UserActivityService {
    private final UserActivityRepository userActivityRepository;

    public UserActivityServiceImpl(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    public void logUserActivity(String phoneNumber, String status) {
        UserActivity activity = UserActivity.builder()
                .phoneNumber(phoneNumber)
                .status(status)
                .build();

        userActivityRepository.save(activity);
    }
}

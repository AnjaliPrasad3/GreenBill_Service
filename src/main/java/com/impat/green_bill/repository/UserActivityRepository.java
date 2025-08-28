package com.impat.green_bill.repository;

import com.impat.green_bill.entities.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserActivityRepository extends MongoRepository<UserActivity, String> {
}

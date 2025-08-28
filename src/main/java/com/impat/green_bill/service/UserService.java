package com.impat.green_bill.service;

import com.impat.green_bill.dtos.UserDto;
import com.impat.green_bill.entities.User;

public interface UserService {
    void createUser(UserDto user);
    public boolean isUserExist(UserDto userDto);
    boolean isUserExistByPhone(String phoneNumber);
    public User getUserByPhone(String phoneNumber);
}

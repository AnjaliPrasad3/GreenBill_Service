package com.impat.green_bill.service.impl;

import com.impat.green_bill.service.UserService;
import com.impat.green_bill.dtos.UserDto;
import com.impat.green_bill.entities.User;
import com.impat.green_bill.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Validated
    public void createUser(@Valid UserDto userDto) {
        User userEntity = User.builder()
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public boolean isUserExist(UserDto userDto) {
        return userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isPresent() ||
                userRepository.findByEmail(userDto.getEmail()).isPresent();
    }

    @Override
    public boolean isUserExistByPhone(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Override
    public User getUserByPhone(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found with phone number: " + phoneNumber));
    }
}

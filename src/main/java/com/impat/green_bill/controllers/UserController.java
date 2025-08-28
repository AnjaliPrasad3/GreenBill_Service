package com.impat.green_bill.controllers;

import com.impat.green_bill.dtos.*;
import com.impat.green_bill.entities.User;
import com.impat.green_bill.service.OtpService;
import com.impat.green_bill.service.UserActivityService;
import com.impat.green_bill.service.UserService;
import com.impat.green_bill.authentication.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final UserActivityService userActivityService;




    public UserController(UserService userService, JwtService jwtService, OtpService otpService,UserActivityService userActivityService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.otpService = otpService;
        this.userActivityService = userActivityService;
    }

    @PostMapping("/login/otp/generate")
    public ResponseEntity<GeneralResponseDto> sendPhoneOtp(@RequestBody PhoneNumberDto phoneDto) {
        String phoneNumber = phoneDto.getPhoneNumber();
        boolean userExists = userService.isUserExistByPhone(phoneNumber);

        if (!userExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GeneralResponseDto("User not registered. Please sign up first.", HttpStatus.NOT_FOUND.value()));
        }
        String otpSid = otpService.sendPhoneOtp(phoneNumber);
        return ResponseEntity.ok(new GeneralResponseDto("OTP Sent! SID: " + otpSid, HttpStatus.OK.value()));
    }

    @PostMapping("/login/otp/verify")
    public ResponseEntity<LoginResponseDto> verifyPhoneOtp(@RequestBody UserActivityDto userActivityDto, @RequestParam String otp) {
        boolean isOtpValid = otpService.verifyPhoneOtp(userActivityDto.getPhoneNumber(), otp);

        User user = userService.getUserByPhone(userActivityDto.getPhoneNumber());
        String role = user.getRole().name();

        String status = isOtpValid ? "success" : "failed";
        userActivityService.logUserActivity(userActivityDto.getPhoneNumber(), status);

        if (isOtpValid) {
            String jwtToken = jwtService.generateToken(userActivityDto.getPhoneNumber(),role);

            LoginResponseDto loginResponse = LoginResponseDto.builder()
                    .token(jwtToken)
                    .expiresIn(jwtService.getExpirationTime())
                    .message("Login successful.")
                    .build();

            return ResponseEntity.ok(loginResponse);
        }

        return ResponseEntity.badRequest().body(LoginResponseDto.builder()
                .message("Invalid OTP.")
                .build());
    }

    @PostMapping("/signup/otp/generate")
    public ResponseEntity<GeneralResponseDto> sendEmailOtp(@RequestBody UserDto userDto) {
        if (userService.isUserExist(userDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new GeneralResponseDto("User already exists!", HttpStatus.CONFLICT.value()));
        }
        otpService.sendEmailOtp(userDto.getEmail());
        otpService.sendPhoneOtp(userDto.getPhoneNumber());
        return ResponseEntity.ok(new GeneralResponseDto("Email and Phone OTP Sent!", HttpStatus.OK.value()));
    }

    @PostMapping("/signup/otp/verify")
    public ResponseEntity<LoginResponseDto> verifyEmailOtp(@RequestBody UserDto userDto, @RequestParam String otpE, @RequestParam String otpP) {
        boolean isOtpValidE = otpService.verifyEmailOtp(userDto.getEmail(), otpE);
        boolean isOtpValidP = otpService.verifyPhoneOtp(userDto.getPhoneNumber(), otpP);


        LoginResponseDto loginResponse;

        if (isOtpValidE && isOtpValidP) {
            userService.createUser(userDto);
            String jwtToken = jwtService.generateToken(userDto.getPhoneNumber(),userDto.getRole().name());

            loginResponse = LoginResponseDto.builder()
                    .token(jwtToken)
                    .expiresIn(jwtService.getExpirationTime())
                    .message("OTP verification successful.")
                    .build();

            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.badRequest().body(LoginResponseDto.builder()
                .message("Invalid OTP.")
                .build());
    }
}

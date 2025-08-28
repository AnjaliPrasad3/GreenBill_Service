
package com.impat.green_bill.controllers;

import com.impat.green_bill.service.OtpService;
import com.impat.green_bill.dtos.GeneralResponseDto;
import com.impat.green_bill.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/otp")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/phone/generate")
    public ResponseEntity<GeneralResponseDto> sendPhoneOtp(@RequestBody String phoneNumber) {
        String otpSid = otpService.sendPhoneOtp(phoneNumber);
        return ResponseEntity.ok(new GeneralResponseDto("OTP Sent! SID: " + otpSid, HttpStatus.OK.value()));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/phone/verify")
    public ResponseEntity<GeneralResponseDto> verifyPhoneOtp(@RequestBody UserDto userDto, @RequestParam String otp) {
        boolean isOtpValid = otpService.verifyPhoneOtp(userDto.getPhoneNumber(), otp);

        if (!isOtpValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP!");
        }
        return ResponseEntity.ok(new GeneralResponseDto("OTP verified" , HttpStatus.OK.value()));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/email/generate")
    public ResponseEntity<GeneralResponseDto> sendEmailOtp(@RequestBody String email) {
        String otpSid = otpService.sendEmailOtp(email);
        return ResponseEntity.ok(new GeneralResponseDto("OTP Sent! SID: " + otpSid, HttpStatus.OK.value()));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/email/verify")
    public ResponseEntity<GeneralResponseDto> verifyEmailOtp(@RequestBody UserDto userDto, @RequestParam String otp) {
        boolean isOtpValid = otpService.verifyEmailOtp(userDto.getEmail(),otp);

        if (!isOtpValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP!");
        }

        return ResponseEntity.ok(new GeneralResponseDto("OTP verified" , HttpStatus.OK.value()));
    }
}
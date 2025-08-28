package com.impat.green_bill.service;

public interface OtpService {
    public String sendPhoneOtp(String phoneNumber);
    public boolean verifyPhoneOtp(String phoneNumber, String otp);
    public String sendEmailOtp(String email);
    public boolean verifyEmailOtp(String email, String otp);
}

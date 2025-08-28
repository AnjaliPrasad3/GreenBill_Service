package com.impat.green_bill.service.impl;

import com.impat.green_bill.service.OtpService;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioOtpServiceImpl implements OtpService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioOtpServiceImpl.class);
    @Value("${twilio.verify_service_sid}")
    private String verifyServiceSid;


    @Override
    public String sendPhoneOtp(String phoneNumber) {
        logger.info("Sending OTP to: {}", phoneNumber);
        Verification verification = Verification.creator(verifyServiceSid, phoneNumber, "sms").create();
        return verification.getSid();
    }

    @Override
    public boolean verifyPhoneOtp(String phoneNumber, String otp) {
        logger.info("Verifying OTP for phone number: {}", phoneNumber);
        VerificationCheck verificationCheck = VerificationCheck.creator(verifyServiceSid)
                .setTo(phoneNumber)
                .setCode(otp)
                .create();
        return "approved".equals(verificationCheck.getStatus());
    }

    @Override
    public String sendEmailOtp(String email) {
        Verification verification = Verification.creator(verifyServiceSid, email, "email").create();
        return verification.getSid();
    }

    @Override
    public boolean verifyEmailOtp(String email, String otp) {
        VerificationCheck verificationCheck = VerificationCheck.creator(verifyServiceSid)
                .setTo(email)
                .setCode(otp)
                .create();
        return "approved".equals(verificationCheck.getStatus());
    }
}

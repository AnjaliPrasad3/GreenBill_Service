package com.impat.green_bill.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String token;
    private long expiresIn;
    private String message;
}

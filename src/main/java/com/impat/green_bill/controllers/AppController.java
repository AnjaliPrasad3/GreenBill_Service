package com.impat.green_bill.controllers;

import com.impat.green_bill.dtos.GeneralResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health")
public class AppController {

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<GeneralResponseDto> health() {
        GeneralResponseDto response = new GeneralResponseDto("Service is up and running", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}

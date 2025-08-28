package com.impat.green_bill.controllers;
import com.impat.green_bill.service.SecretService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secrets")
public class SecretController {
    private final SecretService secretService;

    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/store")
    public ResponseEntity<String> storeSecret(@RequestParam String name, @RequestParam String value) {
        secretService.saveSecret(name, value);
        return ResponseEntity.ok("Secret stored successfully");
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/fetch")
    public ResponseEntity<String> getSecretEncrypted(@RequestParam String name) {
        String encryptedSecret = secretService.getEncryptedSecretForFrontend(name);
        return ResponseEntity.ok(encryptedSecret);
    }
}

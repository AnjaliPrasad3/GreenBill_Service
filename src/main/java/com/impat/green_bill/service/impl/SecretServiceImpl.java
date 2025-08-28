package com.impat.green_bill.service.impl;
import com.impat.green_bill.entities.Secrets;
import com.impat.green_bill.repository.SecretRepository;
import com.impat.green_bill.service.SecretService;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class SecretServiceImpl implements SecretService{
    private final SecretRepository secretRepository;
    private static final String SECRET_KEY = "1234567890123456";

    public SecretServiceImpl(SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    private String encrypt(String value) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(value.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    @Override
    public void saveSecret(String name, String value) {
        Secrets secret = Secrets.builder()
                .name(name)
                .value(value)
                .build();
        secretRepository.save(secret);
    }

    @Override
    public String getEncryptedSecretForFrontend(String name) {
        Secrets secret = secretRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Secret not found: " + name));

        return encrypt(secret.getValue());
    }
}

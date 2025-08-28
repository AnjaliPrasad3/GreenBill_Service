package com.impat.green_bill.service;

public interface SecretService {
    void saveSecret(String name, String value);
    String getEncryptedSecretForFrontend(String name);
}

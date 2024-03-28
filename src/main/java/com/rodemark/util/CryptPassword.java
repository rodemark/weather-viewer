package com.rodemark.util;

public interface CryptPassword {
    String getEncryptPassword(String originalPassword);

    boolean isEncryptPassword(String originalPassword, String encryptPassword);
}

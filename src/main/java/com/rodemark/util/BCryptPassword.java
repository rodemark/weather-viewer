package com.rodemark.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPassword implements CryptPassword{

    @Override
    public String getEncryptPassword(String originalPassword) {
        return BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
    }

    @Override
    public boolean isEncryptPassword(String originalPassword, String encryptPassword) {
        return BCrypt.checkpw(originalPassword, encryptPassword);
    }
}
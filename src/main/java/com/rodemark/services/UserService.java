package com.rodemark.services;

import com.rodemark.models.UserAccount;
import com.rodemark.repositories.UserAccountRepository;
import com.rodemark.util.BCryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserAccountRepository userAccountRepository;
    private final BCryptPassword bCryptPassword;

    @Autowired
    public UserService(UserAccountRepository userAccountRepository, BCryptPassword bCryptPassword) {
        this.userAccountRepository = userAccountRepository;
        this.bCryptPassword = bCryptPassword;
    }

    @Transactional
    public void save(UserAccount userAccount){
        userAccountRepository.save(userAccount);
    }

    public UserAccount findByLogin(UserAccount userAccount){
        return userAccountRepository.findByLogin(userAccount.getLogin()).orElse(null);
    }

    public UserAccount findByLoginAndPassword(UserAccount userAccount){
        String login = userAccount.getLogin();
        String password = userAccount.getPassword();
        UserAccount userAccountByLogin = findByLogin(userAccount);

        if (userAccountByLogin != null){
            String encryptPassword = userAccountByLogin.getPassword();
            if (bCryptPassword.isEncryptPassword(password, encryptPassword)){
                return userAccountRepository.findByLoginAndPassword(login, password).orElse(null);
            }
        }
        return null;
    }
}

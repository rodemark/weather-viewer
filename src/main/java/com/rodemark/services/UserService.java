package com.rodemark.services;

import com.rodemark.models.UserAccount;
import com.rodemark.repositories.UserAccountRepository;
import com.rodemark.util.BCryptPassword;
import com.rodemark.util.CryptPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserAccountRepository userAccountRepository;
    private final CryptPassword cryptPassword;

    @Autowired
    public UserService(UserAccountRepository userAccountRepository, BCryptPassword cryptPassword) {
        this.userAccountRepository = userAccountRepository;
        this.cryptPassword = cryptPassword;
    }

    @Transactional
    public void save(UserAccount userAccount){
        userAccountRepository.save(userAccount);
    }

    public UserAccount findByLogin(String login){
        return userAccountRepository.findByLogin(login).orElse(null);
    }

    public UserAccount findByLoginAndPassword(UserAccount userAccount){
        String login = userAccount.getLogin();
        String password = userAccount.getPassword();
        UserAccount foundedUserAccount = findByLogin(userAccount.getLogin());

        if (foundedUserAccount != null){
            String encryptPassword = foundedUserAccount.getPassword();
            if (cryptPassword.isEncryptPassword(password, encryptPassword)){
                return userAccountRepository.findByLoginAndPassword(login, encryptPassword).orElse(null);
            }
        }
        return null;
    }
}

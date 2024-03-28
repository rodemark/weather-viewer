package com.rodemark.repositories;


import com.rodemark.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByLogin(String login);

    Optional<UserAccount> findByLoginAndPassword(String login, String password);
}

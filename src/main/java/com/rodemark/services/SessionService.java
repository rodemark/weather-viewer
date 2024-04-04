package com.rodemark.services;

import com.rodemark.models.Session;
import com.rodemark.models.UserAccount;
import com.rodemark.repositories.SessionRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Getter
public class SessionService {
    private final SessionRepository sessionRepository;
    private final long SESSION_TIME = 1000 * 60 * 60;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public UUID saveSessionAndGetUUID(UserAccount userAccount){
        Session session = new Session();
        Timestamp expiresAtTime = new Timestamp(System.currentTimeMillis() + SESSION_TIME);
        session.setExpiresAt(expiresAtTime);
        session.setUserAccount(userAccount);
        sessionRepository.save(session);

        return session.getId();
    }

}

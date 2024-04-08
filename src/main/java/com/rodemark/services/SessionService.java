package com.rodemark.services;

import com.rodemark.models.Session;
import com.rodemark.models.UserAccount;
import com.rodemark.repositories.SessionRepository;
import jakarta.servlet.http.Cookie;
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

    @Transactional
    public UUID saveSessionAndGetUUID(UserAccount userAccount){
        Session session = new Session();
        Timestamp expiresAtTime = new Timestamp(System.currentTimeMillis() + SESSION_TIME);
        session.setExpiresAt(expiresAtTime);
        session.setUserAccount(userAccount);
        sessionRepository.save(session);

        return session.getId();
    }

    public Session getSessionByUUID(String sessionUUID){
        UUID uuid = UUID.fromString(sessionUUID);
        return sessionRepository.findById(uuid).orElse(null);
    }

    public UserAccount getUserByUUID(String sessionUUID){
        Session session = getSessionByUUID(sessionUUID);
        return session.getUserAccount();
    }

    public boolean isOver(String sessionUUID){
        Session session = getSessionByUUID(sessionUUID);
        Timestamp expiresAt = session.getExpiresAt();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        return currentTime.after(expiresAt);
    }

    public Cookie getCleanCookie(){
        Cookie cookie = new Cookie("session_id", "");
        cookie.setMaxAge(0);
        return cookie;
    }

    @Transactional
    public void deleteSession(String sessionUUID) {
        UUID uuid = UUID.fromString(sessionUUID);
        sessionRepository.deleteById(uuid);
    }

}

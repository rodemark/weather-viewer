package com.rodemark.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "sessions")
@Entity
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @Column(name = "expires_at")
    private Timestamp expiresAt;

}

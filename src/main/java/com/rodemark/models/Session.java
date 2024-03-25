package com.rodemark.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name = "sessions")
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(generator = "uuid-generator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

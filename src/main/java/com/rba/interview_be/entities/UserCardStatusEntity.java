package com.rba.interview_be.entities;

import com.rba.interview_be.enums.CardStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user_card_status")
@Getter
@Setter
public class UserCardStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatusEnum status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}

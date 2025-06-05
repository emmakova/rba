package com.rba.interview_be.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="oib", unique = true, nullable = false)
    @Pattern(regexp = "\\d{11}")
    private String oib;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<UserCardStatusEntity> cardStatuses;
}

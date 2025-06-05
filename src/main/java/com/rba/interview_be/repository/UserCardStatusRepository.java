package com.rba.interview_be.repository;

import com.rba.interview_be.entities.UserCardStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardStatusRepository extends JpaRepository<UserCardStatusEntity, Integer> {
    void deleteAllByUserId(Integer userId);
}

package com.challenge.userauthenticate.repository;

import com.challenge.userauthenticate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    @Transactional
    @Modifying
    @Query("update User u set u.status = 'ACTIVE', u.failedAttempts = 0, u.lockedDate = null where u.lockedDate < :date")
    void unlockUsers(@Param("date") Date date);
}

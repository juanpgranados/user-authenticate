package com.challenge.userauthenticate.repository;

import com.challenge.userauthenticate.entity.LoginAttempts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAttemptsRepository extends JpaRepository<LoginAttempts, Long> {
}

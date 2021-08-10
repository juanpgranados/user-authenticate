package com.challenge.userauthenticate.service;

import com.challenge.userauthenticate.entity.LoginAttempts;
import com.challenge.userauthenticate.model.LoginAttemptModel;
import com.challenge.userauthenticate.repository.LoginAttemptsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {

    @Autowired
    LoginAttemptsRepository loginAttemptsRepository;

    public void storeAttempt(LoginAttemptModel loginAttempt){
        LoginAttempts loginAttemptEntity = new ModelMapper().map(loginAttempt, LoginAttempts.class);
        loginAttemptsRepository.save(loginAttemptEntity);
    }
}

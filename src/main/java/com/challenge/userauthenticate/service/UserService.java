package com.challenge.userauthenticate.service;

import com.challenge.userauthenticate.model.UserModel;
import com.challenge.userauthenticate.repository.UserRepository;
import com.challenge.userauthenticate.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public static final String ACTIVE_USER = "ACTIVE";
    public static final String LOCKED_USER = "LOCKED";
    public static final int MAX_FAILED_ATTEMPTS = 3;

    public UserModel getUserByUsername(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.map(value -> new ModelMapper().map(value, UserModel.class)).orElse(null);
    }

    public void updateStatus(String userName, String status){
       Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            user.get().setFailedAttempts(3);
            user.get().setStatus(status);
            user.get().setLockedDate(new Date());
            userRepository.save(user.get());
        }
    }

    public void increaseFailedAttempts(String userName){
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent()) {
            user.get().setFailedAttempts(user.get().getFailedAttempts() + 1);
            userRepository.save(user.get());
        }
    }

    public void unlockUsers(Date date){
        userRepository.unlockUsers(date);
    }
}

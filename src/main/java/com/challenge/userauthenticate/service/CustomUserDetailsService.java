package com.challenge.userauthenticate.service;

import com.challenge.userauthenticate.entity.User;
import com.challenge.userauthenticate.model.CustomUserDetailsModel;
import com.challenge.userauthenticate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.map(CustomUserDetailsModel::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + userName + " not found"));
    }
}

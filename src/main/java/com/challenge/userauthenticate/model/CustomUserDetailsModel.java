package com.challenge.userauthenticate.model;

import com.challenge.userauthenticate.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetailsModel implements UserDetails {
    private String userName;
    private String password;
    private boolean status;
    private List<GrantedAuthority> authorities;

    private static Map<String, Boolean> userStatus;
    static {
        userStatus = new HashMap<>();
        userStatus.put("ACTIVE", true);
        userStatus.put("LOCKED", false);
    }

    public CustomUserDetailsModel(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.status = userStatus.get(user.getStatus());
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.challenge.userauthenticate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginAttemptModel {
    private String userName;
    private Boolean loginSuccessful;
    private Integer attempt;
    private Date loginAttemptDate;
    private String userStatus;
}

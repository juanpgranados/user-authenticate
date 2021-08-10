package com.challenge.userauthenticate.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModel {

    private Long id;
    private String userName;
    private String password;
    private String roles;
    private String name;
    private String status;
    private Integer failedAttempts;
    private Date lockedDate;
}

package com.example.testproject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCreds implements Serializable {
    private String username;
    private String password;
}

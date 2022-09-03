package com.sparrow.sparrow.config.oauth.dto;

import com.sparrow.sparrow.domain.user.User;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
    }
}

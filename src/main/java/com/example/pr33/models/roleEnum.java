package com.example.pr33.models;

import org.springframework.security.core.GrantedAuthority;

public enum roleEnum implements GrantedAuthority {
    USER, ADMIN, EMPLOYEE;
    @Override
    public String getAuthority()
    {
        return name();
    }
}

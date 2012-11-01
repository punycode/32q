package de.punyco.thirtytwosquare.domain;

import org.springframework.security.core.GrantedAuthority;


public enum Roles implements GrantedAuthority {

    USER,
    ADMIN,
    UNREGISTERED;

    @Override
    public String getAuthority() {

        return "ROLE_" + toString();
    }
}

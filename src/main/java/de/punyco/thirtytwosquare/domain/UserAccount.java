package de.punyco.thirtytwosquare.domain;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;


@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = String.class)
@RooSerializable
public class UserAccount implements UserDetails {

    private static final String DUMMY_PASSWORD = "N/A";

    @NotEmpty
    private String userId;

    private String nickname;

    @NotEmpty
    private String email;

    @ElementCollection
    private Set<Roles> authorities;

    public UserAccount() {

        authorities = Collections.singleton(Roles.USER);
    }

    public static de.punyco.thirtytwosquare.domain.UserAccount withGoogleId(String userId) {

        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userId);

        return userAccount;
    }


    @Override
    public Set<Roles> getAuthorities() {

        if (getId() == null) {
            HashSet<Roles> effectiveAuthorities = new HashSet<Roles>(authorities);
            effectiveAuthorities.add(Roles.UNREGISTERED);

            return Collections.unmodifiableSet(effectiveAuthorities);
        } else {
            return authorities;
        }
    }


    @Override
    public String getPassword() {

        return DUMMY_PASSWORD;
    }


    @Override
    public String getUsername() {

        return userId;
    }


    @Override
    public boolean isAccountNonExpired() {

        return true;
    }


    @Override
    public boolean isAccountNonLocked() {

        return true;
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

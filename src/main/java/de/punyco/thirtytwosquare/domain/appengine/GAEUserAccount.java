package de.punyco.thirtytwosquare.domain.appengine;

import de.punyco.thirtytwosquare.domain.UserAccount;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@RooJavaBean
@RooToString
@RooJpaEntity
public class GAEUserAccount extends UserAccount {
}

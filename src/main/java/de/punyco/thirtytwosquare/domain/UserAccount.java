package de.punyco.thirtytwosquare.domain;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import org.springframework.security.core.userdetails.UserDetails;


@RooJavaBean
@RooToString
@RooJpaEntity(identifierField = "userId", identifierType = String.class)
@RooEquals
@RooSerializable
public abstract class UserAccount {
}

package de.punyco.thirtytwosquare.domain;

import org.datanucleus.api.jpa.annotations.Extension;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;


@RooJavaBean
@RooToString
@RooJpaEntity(identifierField = "userId", identifierType = String.class)
@RooSerializable
public class UserAccount {

    @NotNull
    private String nickname;
}

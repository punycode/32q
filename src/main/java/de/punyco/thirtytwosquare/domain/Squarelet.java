package de.punyco.thirtytwosquare.domain;

import org.datanucleus.api.jpa.annotations.Extension;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotNull;


@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = String.class)
@RooSerializable
public class Squarelet {

    @RooUploadedFile(contentType = "image/png")
    @Lob
    private byte[] content;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Squarelet> replies = new HashSet<Squarelet>();
}

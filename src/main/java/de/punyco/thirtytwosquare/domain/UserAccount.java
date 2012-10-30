package de.punyco.thirtytwosquare.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.datanucleus.api.jpa.annotations.Extension;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

import javax.persistence.*;

import javax.validation.constraints.NotNull;


@Entity
@RooJavaBean
@RooToString
@RooJpaEntity(identifierField = "userId", identifierType = String.class)
@RooSerializable
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String userId;

    @Version
    private Integer version;

    @NotNull
    private String nickname;

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Squarelet)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        UserAccount rhs = (UserAccount) obj;

        return new EqualsBuilder().append(userId, rhs.userId).isEquals();
    }


    @Override
    public int hashCode() {

        return new HashCodeBuilder().append(userId).toHashCode();
    }


    @Override
    public String toString() {

        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    public Integer getVersion() {

        return this.version;
    }


    public String getUserId() {

        return this.userId;
    }


    public void setVersion(Integer version) {

        this.version = version;
    }


    public void setUserId(String id) {

        this.userId = id;
    }


    public String getNickname() {

        return this.nickname;
    }


    public void setNickname(String nickname) {

        this.nickname = nickname;
    }
}

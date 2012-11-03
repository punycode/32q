package de.punyco.thirtytwosquare.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.datanucleus.api.jpa.annotations.Extension;

import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@Entity
@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = String.class)
@RooSerializable
public class UserAccount implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DUMMY_PASSWORD = "N/A";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Integer version;

    @NotEmpty
    private String userId;

    private String nickname;

    @NotEmpty
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Roles> authorities = new HashSet<Roles>();

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private Set<Squarelet> squarelets = new HashSet<Squarelet>();

    protected UserAccount() {
    }


    public UserAccount(String userId) {

        this.userId = userId;
        authorities.add(Roles.USER);
        authorities.add(Roles.UNREGISTERED);
    }

    public static UserAccount withGoogleId(String userId) {

        return new UserAccount(userId);
    }


    public String getId() {

        return this.id;
    }


    public void setId(String id) {

        this.id = id;
    }


    public String getEmail() {

        return this.email;
    }


    public void setEmail(String email) {

        this.email = email;
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


    public Set<Squarelet> getSquarelets() {

        return squarelets;
    }


    protected void setSquarelets(Set<Squarelet> squarelets) {

        this.squarelets = squarelets;
    }


    public void addSquarelet(Squarelet squarelet) {

        squarelet.setCreatedBy(this);
    }


    void internalAddSquarelet(Squarelet squarelet) {

        this.squarelets.add(squarelet);
    }


    void internalRemoveSquarelet(Squarelet squarelet) {

        this.squarelets.remove(squarelet);
    }


    // --- UserDetails contract --

    public Set<Roles> getAuthorities() {

        return authorities;
    }


    public void setAuthorities(Set<Roles> authorities) {

        this.authorities = authorities;
    }


    @Override
    public String getPassword() {

        return DUMMY_PASSWORD;
    }


    @Override
    public String getUsername() {

        return nickname;
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


    // -- Object contract

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
}

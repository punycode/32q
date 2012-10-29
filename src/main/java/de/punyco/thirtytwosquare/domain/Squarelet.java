package de.punyco.thirtytwosquare.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;


@RooJavaBean
@RooToString
@RooJpaEntity
@RooEquals
@RooSerializable
@Entity
public class Squarelet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    private String metadata;


    public Long getId() {

        return this.id;
    }


    public void setId(Long id) {

        this.id = id;
    }


    public Integer getVersion() {

        return this.version;
    }


    public void setVersion(Integer version) {

        this.version = version;
    }


    public String getMetadata() {

        return this.metadata;
    }


    public void setMetadata(String metadata) {

        this.metadata = metadata;
    }


    public boolean equals(Object obj) {

        if (!(obj instanceof Squarelet)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Squarelet rhs = (Squarelet) obj;
        return new EqualsBuilder().append(id, rhs.id).append(metadata, rhs.metadata).isEquals();
    }


    public int hashCode() {

        return new HashCodeBuilder().append(id).append(metadata).toHashCode();
    }


    public String toString() {

        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}

package de.punyco.thirtytwosquare.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.datanucleus.api.jpa.annotations.Extension;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


@RooJavaBean
@RooToString
@RooJpaEntity(identifierType = String.class)
@RooSerializable
@Entity
public class Squarelet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    private String id;

    @Version
    private Integer version;

    @RooUploadedFile(contentType = "image/png")
    @Lob
    private byte[] content;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Squarelet> replies = new HashSet<Squarelet>();

    public String getId() {

        return this.id;
    }


    public void setId(String id) {

        this.id = id;
    }


    public Integer getVersion() {

        return this.version;
    }


    public void setVersion(Integer version) {

        this.version = version;
    }


    public byte[] getContent() {

        return this.content;
    }


    public void setContent(byte[] content) {

        this.content = content;
    }


    public Set<Squarelet> getReplies() {

        return this.replies;
    }


    public void setReplies(Set<Squarelet> replies) {

        this.replies = replies;
    }


    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Squarelet)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        Squarelet rhs = (Squarelet) obj;

        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }


    @Override
    public int hashCode() {

        return new HashCodeBuilder().append(id).toHashCode();
    }


    @Override
    public String toString() {

        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

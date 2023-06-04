package com.inpress.optimusweather.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.Instant;

/**
 * The base entity containing the auditable fields.
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity implements Serializable {

    /**
     * Represents the username who created this entity object.
     */
    @CreatedBy
    @Column(name = "created_by")
    protected String createdBy;

    /**
     * Represents the creation date of this entity object.
     */
    @CreatedDate
    @Column(name = "created_date")
    protected Instant createdDate = Instant.now();

    /**
     * Represents the last username who updated this entity object.
     */
    @LastModifiedBy
    @Column(name = "last_modified_by")
    protected String lastModifiedBy;

    /**
     * Represents the last modification date of this entity object.
     */
    @LastModifiedDate
    @Column(name = "last_modified_date")
    protected Instant lastModifiedDate = Instant.now();

    /**
     * Represents the object version used by JPA optimistic locking mechanism.
     */
    @Version
    private int version;
}
